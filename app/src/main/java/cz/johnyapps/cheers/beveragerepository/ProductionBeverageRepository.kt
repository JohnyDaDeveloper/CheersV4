package cz.johnyapps.cheers.beveragerepository

import cz.johnyapps.cheers.beveragedatabase.BeverageDatabase
import cz.johnyapps.cheers.beveragedatabase.dto.BeverageDbEntity
import cz.johnyapps.cheers.beveragedatabase.dto.CategoryDbEntity
import cz.johnyapps.cheers.beveragedatabase.dto.CounterDbEntity
import cz.johnyapps.cheers.global.dto.Beverage
import cz.johnyapps.cheers.global.dto.Category
import cz.johnyapps.cheers.global.dto.Counter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class ProductionBeverageRepository(
    private val database: BeverageDatabase
): BeverageRepository {
    override suspend fun insertCategory(category: Category) {
        database.categoryDao().insert(CategoryDbEntity(category))
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return database.categoryDao().getAllWithCounters()
            .map { list -> list.map { category -> category.toGlobalDto() } }
    }

    override fun updateCategory(category: Category) {
        database.categoryDao().update(CategoryDbEntity(category))
    }

    override suspend fun insertCounter(counter: Counter) {
        val id = database.counterDao().insert(CounterDbEntity(counter))
        counter.id = id
    }

    override fun getCounter(counterId: Long): Flow<Counter> {
        return database.counterDao().getWithBeverage(counterId)
            .map {
                it.toGlobalDto()
            }
    }

    override fun getAllCounters(): Flow<List<Counter>> {
        return database.counterDao().getAllWithBeverages().map {
            it.map { counter -> counter.toGlobalDto() }
        }
    }

    override fun updateCounter(counter: Counter) {
        database.counterDao().update(CounterDbEntity(counter))
    }

    override fun updateCounters(counters: List<Counter>) {
        database.counterDao().update(counters.map { CounterDbEntity(it) })
    }

    override fun deleteCounters(counters: List<Counter>) {
        database.counterDao().delete(counters.map { CounterDbEntity(it) })
    }

    override fun insertBeverage(beverage: Beverage) {
        val id = database.beverageDao().insert(BeverageDbEntity(beverage))
        beverage.id = id
    }

    override fun getBeverage(beverageId: Long): Flow<Beverage> {
        return database.beverageDao().get(beverageId).map { it.toGlobalDto() }
    }

    override fun getAllBeverages(): Flow<List<Beverage>> {
        return database.beverageDao().getAll().map {
            it.map { beverage -> beverage.toGlobalDto() }
        }
    }
}