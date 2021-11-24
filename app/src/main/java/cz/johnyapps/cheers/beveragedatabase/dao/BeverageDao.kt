package cz.johnyapps.cheers.beveragedatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.johnyapps.cheers.beveragedatabase.dto.BeverageDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BeverageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(beverage: BeverageDbEntity): Long

    @Query("SELECT * FROM beverage_table")
    fun getAll(): Flow<List<BeverageDbEntity>>
}