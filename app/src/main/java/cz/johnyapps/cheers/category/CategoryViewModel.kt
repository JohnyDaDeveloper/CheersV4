package cz.johnyapps.cheers.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.johnyapps.cheers.beveragerepository.BeverageRepository
import cz.johnyapps.cheers.global.dto.Beverage
import cz.johnyapps.cheers.global.dto.Category
import cz.johnyapps.cheers.global.dto.Counter
import cz.johnyapps.cheers.global.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    application: Application,
    private val repository: BeverageRepository
): AndroidViewModel(application) {
    private val _category = MutableStateFlow<CategoryEntity?>(null)
    val category: StateFlow<CategoryEntity?> = _category

    private val _beverages = MutableStateFlow<List<Beverage>?>(null)
    val beverages: StateFlow<List<Beverage>?> = _beverages

    private val _selectedCounter = MutableStateFlow<Counter?>(null)
    val selectedCounter: StateFlow<Counter?> = _selectedCounter

    private val _counters = MutableStateFlow<List<Counter>?>(null)
    val counters: StateFlow<List<Counter>?> = _counters

    init {
        viewModelScope.launch {
            repository.getAllBeverages().collect {
                _beverages.emit(it)
            }
        }

        viewModelScope.launch {
            category.collect {
                it?.selectedCounter?.collect { counter ->
                    _selectedCounter.emit(counter)
                }
            }
        }

        viewModelScope.launch {
            repository.getAllCounters().collect {
                _counters.emit(it)
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

            Logger.i(TAG, "saveCounter: Saving counter for '${beverage.name}' (id: ${counter.id}, value: ${counter.entries.size})")
            repository.updateCounter(counter)

            val category = category.value
            
            if (category != null) {
                category.selectedCounter.emit(counter)
                repository.updateCategory(category.toGlobalDto())
            } else {
                Logger.w(TAG, "saveCounter: Category is null")
            }
        }
    }

    companion object {
        private const val TAG = "CategoryViewModel"
    }
}