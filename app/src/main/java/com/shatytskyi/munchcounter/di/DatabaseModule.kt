package com.shatytskyi.munchcounter.di

import android.content.Context
import androidx.room.Room
import com.shatytskyi.munchcounter.data.CharacterDao
import com.shatytskyi.munchcounter.data.MunchkinDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideMunchkinDatabase(@ApplicationContext context: Context): MunchkinDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MunchkinDatabase::class.java,
            "munchkin_database"
        ).build()
    }
    
    @Provides
    @Singleton
    fun provideCharacterDao(database: MunchkinDatabase): CharacterDao {
        return database.characterDao()
    }
}