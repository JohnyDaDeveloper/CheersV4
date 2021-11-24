package cz.johnyapps.cheers.beveragedatabase.dao

import androidx.room.*
import cz.johnyapps.cheers.beveragedatabase.dto.CounterAndBeverageDbEntity
import cz.johnyapps.cheers.beveragedatabase.dto.CounterDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(counter: CounterDbEntity): Long

    @Update
    fun update(counter: CounterDbEntity)

    @Transaction
    @Query("SELECT * FROM counter_table WHERE counter_id = :counterId")
    fun getWithBeverage(counterId: Long): Flow<CounterAndBeverageDbEntity>
}