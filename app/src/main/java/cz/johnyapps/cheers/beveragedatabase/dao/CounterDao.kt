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

    @Update
    fun update(counters: List<CounterDbEntity>)

    @Delete
    fun delete(counters: List<CounterDbEntity>)

    @Transaction
    @Query("SELECT * FROM counter_table WHERE counter_id = :counterId")
    fun getWithBeverage(counterId: Long): Flow<CounterAndBeverageDbEntity>

    @Transaction
    @Query("SELECT * FROM counter_table WHERE active = 1")
    fun getAllWithBeverages(): Flow<List<CounterAndBeverageDbEntity>>
}