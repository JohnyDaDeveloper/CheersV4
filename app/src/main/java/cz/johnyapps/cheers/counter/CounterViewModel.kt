package cz.johnyapps.cheers.counter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.johnyapps.cheers.global.utils.Logger
import cz.johnyapps.cheers.beveragerepository.BeverageRepository
import cz.johnyapps.cheers.counter.dto.CounterEntity
import cz.johnyapps.cheers.global.dto.Counter
import cz.johnyapps.cheers.global.dto.Entry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.ArrayList

@FlowPreview
@HiltViewModel
class CounterViewModel @Inject constructor(
    application: Application,
    repository: BeverageRepository
): AndroidViewModel(application) {
    private val _counter: MutableStateFlow<CounterEntity?> = MutableStateFlow(null)
    val counter: Flow<CounterEntity?> = _counter

    private val _count: MutableStateFlow<MutableList<Entry>> = MutableStateFlow(ArrayList())
    val count: StateFlow<List<Entry>> = _count

    private var debouncedCounter: Flow<Counter?> = emptyFlow()

    init {
        debouncedCounter = _count.combine(_counter) { _, counter ->
            counter?.toGlobalDto()
        }.debounce(COUNTER_DEBOUNCE_DELAY)

        viewModelScope.launch(Dispatchers.IO) {
            debouncedCounter.collect {
                it?.let {
                    Logger.d(TAG, "Updating counter of '${it.beverage.name}' with ${it.entries.size} entries (id: ${it.id})")
                    repository.updateCounter(it)
                }
            }
        }
    }

    fun setCounter(counter: StateFlow<Counter?>) {
        viewModelScope.launch {
            val counterEntity = counter.value
            _counter.emit(counterEntity?.let { CounterEntity(counterEntity) })

            counter.map {
                it?.let { CounterEntity(it) }
            }.collect {
                _counter.emit(it)
                _counter.value?.entries?.collect { list ->
                    _count.emit(list)
                }
            }
        }
    }

    fun increaseCount() {
        viewModelScope.launch {
            _counter.value?.increaseCount()
        }
    }

    fun decreaseCount() {
        viewModelScope.launch {
            _counter.value?.decreaseCount()
        }
    }

    companion object {
        private const val TAG = "CounterViewModel"
        private const val COUNTER_DEBOUNCE_DELAY = 500L
    }
}