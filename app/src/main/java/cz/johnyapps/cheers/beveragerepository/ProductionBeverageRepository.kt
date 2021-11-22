package cz.johnyapps.cheers.beveragerepository

import cz.johnyapps.cheers.beveragedatabase.BeverageDatabase
import cz.johnyapps.cheers.dto.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductionBeverageRepository(
    private val database: BeverageDatabase
): BeverageRepository {
    override fun insertCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return database.categoryDao().getAll().map { list ->
            list.map { category -> Category(category) }
        }
    }
}