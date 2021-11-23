package cz.johnyapps.cheers.beveragedatabase.dao

import androidx.room.*
import cz.johnyapps.cheers.beveragedatabase.dto.CounterDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(counter: CounterDbEntity)

    @Update
    fun update(counter: CounterDbEntity)

    @Query("SELECT * FROM counter_table WHERE id = :counterId")
    fun get(counterId: Long): Flow<CounterDbEntity>
}