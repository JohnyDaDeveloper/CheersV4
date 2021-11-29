package cz.johnyapps.cheers.beverage

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
class BeverageViewModel @Inject constructor(
    application: Application,
    private val repository: BeverageRepository
): AndroidViewModel(application) {
    private val _beverage = MutableStateFlow<Beverage?>(null)
    val beverage: StateFlow<Beverage?> = _beverage

    fun loadBeverage(beverageId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBeverage(beverageId).collect {
                _beverage.emit(it)
            }
        }
    }
}