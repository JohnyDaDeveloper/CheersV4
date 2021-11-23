package cz.johnyapps.cheers.beveragerepository

import cz.johnyapps.cheers.dto.Category
import cz.johnyapps.cheers.dto.Counter
import kotlinx.coroutines.flow.Flow

interface BeverageRepository {
    fun insertCategory(category: Category)

    fun getAllCategories(): Flow<List<Category>>

    fun insertCounter(counter: Counter)

    fun getCounter(counterId: Long): Flow<Counter>

    fun updateCounter(counter: Counter)
}