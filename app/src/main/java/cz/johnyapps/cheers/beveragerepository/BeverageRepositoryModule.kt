package cz.johnyapps.cheers.beveragerepository

import android.content.Context
import androidx.room.Room
import cz.johnyapps.cheers.beveragedatabase.BeverageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BeverageRepositoryModule {
    @Provides
    @Singleton
    fun provideBeverageRepository(database: BeverageDatabase): BeverageRepository {
        return ProductionBeverageRepository(database)
    }

    @Provides
    @Singleton
    fun provideBeverageDatabase(@ApplicationContext context: Context): BeverageDatabase {
        return Room.databaseBuilder(context, BeverageDatabase::class.java, BeverageDatabase.DATABASE_NAME)
            .build()
    }
}