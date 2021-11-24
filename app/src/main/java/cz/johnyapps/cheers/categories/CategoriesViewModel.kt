package cz.johnyapps.cheers.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cz.johnyapps.cheers.beveragerepository.BeverageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    application: Application,
    repository: BeverageRepository
): AndroidViewModel(application) {
    val categories = repository.getAllCategories()
}