package cz.johnyapps.cheers.beveragedatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import cz.johnyapps.cheers.beveragedatabase.dto.BeverageDbEntity

@Dao
interface BeverageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(beverage: BeverageDbEntity): Long
}