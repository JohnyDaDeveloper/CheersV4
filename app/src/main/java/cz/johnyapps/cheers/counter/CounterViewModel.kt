package cz.johnyapps.cheers.counter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application) {
    private val _count = MutableStateFlow(0)
    val count: Flow<Int> = _count

    fun increaseCount() {
        viewModelScope.launch(Dispatchers.Default) {
            _count.value++
        }
    }

    fun decreaseCount() {
        viewModelScope.launch(Dispatchers.Default) {
            if (_count.value > 0) {
                _count.emit(_count.value - 1)
            }
        }
    }
}