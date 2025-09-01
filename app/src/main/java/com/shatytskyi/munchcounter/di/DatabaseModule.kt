package com.shatytskyi.munchcounter.di

import android.content.Context
import androidx.room.Room
import com.shatytskyi.munchcounter.data.CharacterDao
import com.shatytskyi.munchcounter.data.CharacterRepository
import com.shatytskyi.munchcounter.data.MunchkinDatabase
import com.shatytskyi.munchcounter.data.ThemePreferences
import com.shatytskyi.munchcounter.data.ThemePreferencesImpl
import org.koin.dsl.module

val databaseModule = module {
    
    single<MunchkinDatabase> {
        Room.databaseBuilder(
            get<Context>().applicationContext,
            MunchkinDatabase::class.java,
            "munchkin_database"
        ).build()
    }
    
    single<CharacterDao> {
        get<MunchkinDatabase>().characterDao()
    }
    
    single<ThemePreferences> {
        ThemePreferencesImpl(get())
    }
    
    single<CharacterRepository> {
        CharacterRepository(get())
    }
}