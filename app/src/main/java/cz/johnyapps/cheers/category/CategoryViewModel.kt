package cz.johnyapps.cheers.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.johnyapps.cheers.beveragerepository.BeverageRepository
import cz.johnyapps.cheers.dto.Beverage
import cz.johnyapps.cheers.dto.Category
import cz.johnyapps.cheers.dto.Counter
import cz.johnyapps.cheers.utils.Logger
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
    private val _category = MutableStateFlow<Category?>(null)
    val category: StateFlow<Category?> = _category

    private val _beverages = MutableStateFlow<List<Beverage>?>(null)
    val beverages: StateFlow<List<Beverage>?> = _beverages

    init {
        viewModelScope.launch {
            repository.getAllBeverages().collect {
                _beverages.emit(it)
            }
        }
    }

    fun setCategory(category: Category) {
        _category.value = category
    }

    fun saveCounter(counter: Counter) {
        viewModelScope.launch(Dispatchers.IO) {
            val beverage = counter.beverage

            if (beverage.id == 0L) { //New beverage, needs to be saved
                Logger.i(TAG, "saveCounter: Saving beverage '${beverage.name}'")
                repository.insertBeverage(beverage)
            }

            Logger.i(TAG, "saveCounter: Saving counter for '${beverage.name}' (id: ${beverage.id})")
            repository.insertCounter(counter)
        }
    }

    companion object {
        private const val TAG = "CategoryViewModel"
    }
}