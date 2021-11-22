package cz.johnyapps.cheers.beveragedatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.johnyapps.cheers.beveragedatabase.dao.CategoryDao
import cz.johnyapps.cheers.beveragedatabase.dto.CategoryDbEntity

@Database(version = 1, entities = [CategoryDbEntity::class], exportSchema = true)
abstract class BeverageDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        const val DATABASE_NAME = "beverage_database"
    }
}