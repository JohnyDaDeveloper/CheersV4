package cz.johnyapps.cheers.beverages

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cz.johnyapps.cheers.beveragerepository.BeverageRepository
import cz.johnyapps.cheers.global.dto.Beverage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeveragesViewModel @Inject constructor(
    application: Application,
    repository: BeverageRepository
): AndroidViewModel(application) {
    private val _beverages = MutableStateFlow<List<Beverage>>(ArrayList())
    val beverages: StateFlow<List<Beverage>> = _beverages

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllBeverages().collect {
                _beverages.emit(it)
            }
        }
    }
}