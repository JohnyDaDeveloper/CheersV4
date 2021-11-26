package cz.johnyapps.cheers.beveragerepository

import cz.johnyapps.cheers.global.dto.Beverage
import cz.johnyapps.cheers.global.dto.Category
import cz.johnyapps.cheers.global.dto.Counter
import kotlinx.coroutines.flow.Flow

interface BeverageRepository {
    suspend fun insertCategory(category: Category)
    fun getAllCategories(): Flow<List<Category>>
    fun updateCategory(category: Category)

    suspend fun insertCounter(counter: Counter)
    fun getCounter(counterId: Long): Flow<Counter>
    fun getAllCounters(): Flow<List<Counter>>
    fun updateCounter(counter: Counter)
    fun updateCounters(counters: List<Counter>)
    fun deleteCounters(counters: List<Counter>)

    fun insertBeverage(beverage: Beverage)
    fun getAllBeverages(): Flow<List<Beverage>>
}