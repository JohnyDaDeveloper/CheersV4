package cz.johnyapps.cheers.beveragedatabase.dao

import androidx.room.*
import cz.johnyapps.cheers.beveragedatabase.dto.CategoryAndCounterDbEntity
import cz.johnyapps.cheers.beveragedatabase.dto.CategoryDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: CategoryDbEntity)

    @Transaction
    @Query("SELECT * FROM counter_table")
    fun getAllWithCounters(): Flow<List<CategoryAndCounterDbEntity>>

    @Query("SELECT * FROM category_table WHERE selected_counter_id < 1")
    fun getAllWithoutCounters(): Flow<List<CategoryDbEntity>>
}