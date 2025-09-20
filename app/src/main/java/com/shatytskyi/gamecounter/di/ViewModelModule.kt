package com.shatytskyi.gamecounter.di

import com.shatytskyi.gamecounter.rate.RateAppManager
import com.shatytskyi.gamecounter.rate.RateAppPreferences
import com.shatytskyi.gamecounter.viewmodel.CommonViewModel
import com.shatytskyi.gamecounter.viewmodel.ThemeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    single {
        RateAppPreferences(get())
    }

    single {
        RateAppManager(get(), get(), get())
    }

    viewModel<CommonViewModel> {
        CommonViewModel(get(), get())
    }

    viewModel<ThemeViewModel> {
        ThemeViewModel(get())
    }
}
