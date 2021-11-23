package cz.johnyapps.cheers.category

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.johnyapps.cheers.dto.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application) {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    fun setCategory(category: Category) {
        _category.value = category
    }

    suspend fun changeCount(count: Int) {
        val value = _count.value + count

        Log.d("TAG", "$count")

        if (value > 0 && value != _count.value) {
            _count.emit(value)
        }
    }
}