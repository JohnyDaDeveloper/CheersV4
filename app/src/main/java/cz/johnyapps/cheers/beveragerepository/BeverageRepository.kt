package cz.johnyapps.cheers.beveragerepository

import cz.johnyapps.cheers.dto.Beverage
import cz.johnyapps.cheers.dto.Category
import cz.johnyapps.cheers.dto.Counter
import kotlinx.coroutines.flow.Flow

interface BeverageRepository {
    suspend fun insertCategory(category: Category)
    fun getAllCategories(): Flow<List<Category>>
    fun updateCategory(category: Category)

    suspend fun insertCounter(counter: Counter)
    fun getCounter(counterId: Long): Flow<Counter>
    fun getAllCounters(): Flow<List<Counter>>
    fun updateCounter(counter: Counter)

    fun insertBeverage(beverage: Beverage)
    fun getAllBeverages(): Flow<List<Beverage>>
}