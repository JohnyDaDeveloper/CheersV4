package cz.johnyapps.cheers.beveragedatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.johnyapps.cheers.beveragedatabase.dao.CategoryDao
import cz.johnyapps.cheers.beveragedatabase.dao.CounterDao
import cz.johnyapps.cheers.beveragedatabase.dto.CategoryDbEntity
import cz.johnyapps.cheers.beveragedatabase.dto.CounterDbEntity

@Database(version = 1, entities = [CategoryDbEntity::class, CounterDbEntity::class], exportSchema = true)
@TypeConverters(Converters::class)
abstract class BeverageDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun counterDao(): CounterDao

    companion object {
        const val DATABASE_NAME = "beverage_database"
    }
}