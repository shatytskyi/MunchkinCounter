package com.shatytskyi.munchcounter.di

import android.content.Context
import androidx.room.Room
import com.shatytskyi.munchcounter.data.CharacterDao
import com.shatytskyi.munchcounter.data.CharacterRepository
import com.shatytskyi.munchcounter.data.MunchkinDatabase
import com.shatytskyi.munchcounter.data.ThemePreferences
import com.shatytskyi.munchcounter.data.ThemePreferencesImpl
import com.shatytskyi.munchcounter.data.TimerPreferences
import com.shatytskyi.munchcounter.data.TimerPreferencesImpl
import com.shatytskyi.munchcounter.BuildConfig
import com.shatytskyi.munchcounter.analytics.AnalyticsManager
import com.shatytskyi.munchcounter.analytics.DebugAnalyticsManager
import com.shatytskyi.munchcounter.analytics.ReleaseAnalyticsManager
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
    
    single<AnalyticsManager> {
        if (BuildConfig.DEBUG) {
            DebugAnalyticsManager()
        } else {
            ReleaseAnalyticsManager()
        }
    }
}
