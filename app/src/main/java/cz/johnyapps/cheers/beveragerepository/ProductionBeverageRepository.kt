package cz.johnyapps.cheers.beveragerepository

import cz.johnyapps.cheers.beveragedatabase.BeverageDatabase
import cz.johnyapps.cheers.beveragedatabase.dto.BeverageDbEntity
import cz.johnyapps.cheers.beveragedatabase.dto.CategoryDbEntity
import cz.johnyapps.cheers.beveragedatabase.dto.CounterDbEntity
import cz.johnyapps.cheers.dto.Beverage
import cz.johnyapps.cheers.dto.Category
import cz.johnyapps.cheers.dto.Counter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class ProductionBeverageRepository(
    private val database: BeverageDatabase
): BeverageRepository {
    override suspend fun insertCategory(category: Category) {
        database.categoryDao().insert(CategoryDbEntity(category))
    }

    override fun getAllCategories(): Flow<List<Category>> {
        val withCounters = database.categoryDao().getAllWithCounters()
            .map { list ->
                list.filter { category -> category.category != null }.map { category ->
                    category.toGlobalDto() as Category
                }
            }

        val withoutCounters = database.categoryDao().getAllWithoutCounters().map {
            it.map { category ->
                category.toGlobalDto()
            }
        }

        return withCounters.combine(withoutCounters) { a, b ->
            val mutableA = a.toMutableList()
            mutableA.addAll(b)
            mutableA.sort()
            mutableA
        }
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

    override fun updateCounter(counter: Counter) {
        database.counterDao().update(CounterDbEntity(counter))
    }

    override fun insertBeverage(beverage: Beverage) {
        val id = database.beverageDao().insert(BeverageDbEntity(beverage))
        beverage.id = id
    }

    override fun getAllBeverages(): Flow<List<Beverage>> {
        return database.beverageDao().getAll().map {
            it.map { beverage -> beverage.toGlobalDto() }
        }
    }
}