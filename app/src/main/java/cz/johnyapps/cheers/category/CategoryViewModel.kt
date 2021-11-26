package cz.johnyapps.cheers.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.johnyapps.cheers.beveragerepository.BeverageRepository
import cz.johnyapps.cheers.counter.dto.CounterEntity
import cz.johnyapps.cheers.global.dto.Beverage
import cz.johnyapps.cheers.global.dto.Category
import cz.johnyapps.cheers.global.dto.Counter
import cz.johnyapps.cheers.global.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@FlowPreview
class CategoryViewModel @Inject constructor(
    application: Application,
    private val repository: BeverageRepository
): AndroidViewModel(application) {
    private val _category = MutableStateFlow<CategoryEntity?>(null)
    val category: StateFlow<CategoryEntity?> = _category

    private val _beverages = MutableStateFlow<List<Beverage>?>(null)
    val beverages: StateFlow<List<Beverage>?> = _beverages

    private val _categorySelectedCounter = MutableStateFlow<CounterEntity?>(null)
    val categorySelectedCounter: StateFlow<CounterEntity?> = _categorySelectedCounter

    private val _listSelectedCounter = MutableStateFlow<CounterEntity?>(null)
    val listSelectedCounter: StateFlow<CounterEntity?> = _listSelectedCounter

    private val _counters = MutableStateFlow<List<CounterEntity>?>(null)
    val counters: StateFlow<List<CounterEntity>?> = _counters

    init {
        viewModelScope.launch {
            repository.getAllBeverages().collect {
                _beverages.emit(it)
            }
        }

        viewModelScope.launch {
            category.collect {
                it?.selectedCounter?.map { counter ->
                    counter?.let { CounterEntity(counter) }
                }?.collect { counter ->
                    _categorySelectedCounter.emit(counter)
                }
            }
        }

        viewModelScope.launch {
            repository.getAllCounters().map {
                it.map { counter -> CounterEntity(counter) }
            }.collect {
                _counters.emit(it)
            }
        }

        viewModelScope.launch {
            categorySelectedCounter.collect {
                moveCounterToTop(it, counters.value)
            }
        }

        viewModelScope.launch {
            counters.collect {
                moveCounterToTop(categorySelectedCounter.value, it)
            }
        }
    }

    fun collectCounterUpdate(counter: Flow<CounterEntity>) {
        viewModelScope.launch {
            counter.debounce(500L).collect {
                saveCounter(it.toGlobalDto())
            }
        }

        viewModelScope.launch {
            counter.collect {
                _categorySelectedCounter.emit(it)
            }
        }
    }

    fun stopCounters(counters: List<Counter>) {
        viewModelScope.launch(Dispatchers.IO) {
            Logger.d(TAG, "deleteCounters: Stopping ${counters.size} counters")
            counters.forEach { it.active = false }
            repository.updateCounters(counters)
        }
    }

    fun deleteCounters(counters: List<Counter>) {
        viewModelScope.launch(Dispatchers.IO) {
            Logger.d(TAG, "deleteCounters: Deleting ${counters.size} counters")
            repository.deleteCounters(counters)
        }
    }

    fun collectSelectedCounters(counter: Flow<CounterEntity?>) {
        viewModelScope.launch {
            counter.collect {
                _listSelectedCounter.emit(it)
            }
        }
    }

    fun setCategory(category: Category) {
        viewModelScope.launch {
            _category.emit(CategoryEntity(category))
        }
    }

    fun saveCounter(counter: Counter) {
        viewModelScope.launch(Dispatchers.IO) {
            val beverage = counter.beverage

            if (beverage.id == 0L) { //New beverage, needs to be saved
                Logger.i(TAG, "saveCounter: Saving beverage '${beverage.name}'")
                repository.insertBeverage(beverage)
            }

            if (counter.id == 0L) {
                repository.insertCounter(counter)
                Logger.i(TAG, "saveCounter: Saved counter for '${beverage.name}' (id: ${counter.id}, value: ${counter.entries.size})")
            } else {
                Logger.i(TAG, "saveCounter: Updated counter for '${beverage.name}' (id: ${counter.id}, value: ${counter.entries.size})")
                repository.updateCounter(counter)
            }

            val category = category.value
            
            if (category != null) {
                category.selectedCounter.emit(counter)
                repository.updateCategory(category.toGlobalDto())
            } else {
                Logger.w(TAG, "saveCounter: Category is null")
            }
        }
    }

    private suspend fun moveCounterToTop(counter: CounterEntity?, counters: List<CounterEntity>?) {
        if (counter != null && counters != null && counters.isNotEmpty()) {
            if (counters.first().id != counter.id) {
                val mutableCounters = counters.toMutableList()
                val index = findCounterIndex(counter, mutableCounters)

                if (index != -1) {
                    Logger.d(TAG, "moveCounterToTop: Moving counter from $index to top")

                    val c = mutableCounters[index]
                    mutableCounters.removeAt(index)
                    mutableCounters.add(0, c)

                    _counters.emit(mutableCounters)
                }
            }
        }
    }

    private fun findCounterIndex(counter: CounterEntity, counters: List<CounterEntity>): Int {
        counters.forEachIndexed { index, it ->
            if (counter.id == it.id) {
                return index
            }
        }

        return -1
    }

    companion object {
        private const val TAG = "CategoryViewModel"
    }
}