package cz.johnyapps.cheers.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cz.johnyapps.cheers.beveragerepository.BeverageRepository
import cz.johnyapps.cheers.dto.Category
import cz.johnyapps.cheers.dto.Counter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    application: Application,
    private val repository: BeverageRepository
): AndroidViewModel(application) {
    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    fun setCategory(category: Category) {
        _category.value = category
    }

    fun saveCounter(counter: Counter) {
        viewModelScope.launch(Dispatchers.IO) {
            if (counter.beverage.id == 0L) { //New beverage, needs to be saved
                repository.insertBeverage(counter.beverage)
            }

            repository.insertCounter(counter)
        }
    }
}