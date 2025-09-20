package com.shatytskyi.gamecounter

import android.app.Application
import com.shatytskyi.gamecounter.di.databaseModule
import com.shatytskyi.gamecounter.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MunchkinApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MunchkinApplication)
            modules(
                databaseModule,
                viewModelModule
            )
        }
    }
}
