package cz.johnyapps.cheers.beveragerepository

import cz.johnyapps.cheers.beveragedatabase.BeverageDatabase
import cz.johnyapps.cheers.beveragedatabase.dto.CounterDbEntity
import cz.johnyapps.cheers.dto.Category
import cz.johnyapps.cheers.dto.Counter
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

    override fun insertCounter(counter: Counter) {
        database.counterDao().insert(CounterDbEntity(counter))
    }

    override fun getCounter(counterId: Long): Flow<Counter> {
        return database.counterDao().get(counterId)
            .map {
                it.toGlobalDto()
            }
    }

    override fun updateCounter(counter: Counter) {
        database.counterDao().update(CounterDbEntity(counter))
    }
}