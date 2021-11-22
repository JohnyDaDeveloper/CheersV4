package cz.johnyapps.cheers.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.johnyapps.cheers.dto.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application) {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    fun increaseCount() {
        viewModelScope.launch {
            _count.emit(_count.value + 1)
        }
    }

    fun decreaseCount() {
        viewModelScope.launch {
            _count.emit(_count.value - 1)
        }
    }

    fun setCategory(category: Category) {
        _category.value = category
    }
}