package com.shatytskyi.munchcounter.di

import com.shatytskyi.munchcounter.viewmodel.CommonViewModel
import com.shatytskyi.munchcounter.viewmodel.ThemeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    
    viewModel<CommonViewModel> {
        CommonViewModel(get())
    }
    
    viewModel<ThemeViewModel> {
        ThemeViewModel(get())
    }
}