package com.shatytskyi.gamecounter.di

import android.content.Context
import androidx.room.Room
import com.shatytskyi.gamecounter.data.CharacterDao
import com.shatytskyi.gamecounter.data.CharacterRepository
import com.shatytskyi.gamecounter.data.MunchkinDatabase
import com.shatytskyi.gamecounter.data.ThemePreferences
import com.shatytskyi.gamecounter.data.ThemePreferencesImpl
import com.shatytskyi.gamecounter.data.TimerPreferences
import com.shatytskyi.gamecounter.data.TimerPreferencesImpl
import org.koin.dsl.module

val databaseModule = module {

    single<MunchkinDatabase> {
        Room.databaseBuilder(
            get<Context>().applicationContext,
            MunchkinDatabase::class.java,
            "munchkin_database"
        ).fallbackToDestructiveMigration(
            dropAllTables = true
        ).build()
    }

    single<CharacterDao> {
        get<MunchkinDatabase>().characterDao()
    }

    single<ThemePreferences> {
        ThemePreferencesImpl(get())
    }

    single<TimerPreferences> {
        TimerPreferencesImpl(get())
    }

    single<CharacterRepository> {
        CharacterRepository(get())
    }
}
