package cz.johnyapps.cheers.beveragerepository

import cz.johnyapps.cheers.dto.Category
import kotlinx.coroutines.flow.Flow

interface BeverageRepository {
    fun insertCategory(category: Category)

    fun getAllCategories(): Flow<List<Category>>
}