package cz.johnyapps.cheers.beveragedatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.johnyapps.cheers.beveragedatabase.dto.CategoryDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryDbEntity)

    @Query("SELECT * FROM category_table")
    fun getAll(): Flow<List<CategoryDbEntity>>
}