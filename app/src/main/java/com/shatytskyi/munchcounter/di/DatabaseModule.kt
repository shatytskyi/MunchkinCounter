package com.shatytskyi.munchcounter.di

import android.content.Context
import androidx.room.Room
import com.shatytskyi.munchcounter.data.CharacterDao
import com.shatytskyi.munchcounter.data.MunchkinDatabase
import com.shatytskyi.munchcounter.data.ThemePreferences
import com.shatytskyi.munchcounter.data.ThemePreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {
    
    companion object {
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
    
    @Binds
    @Singleton
    abstract fun bindThemePreferences(impl: ThemePreferencesImpl): ThemePreferences
}