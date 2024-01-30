package com.syntaxticsugr.callerid.di

import com.syntaxticsugr.callerid.viewmodel.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val calleridModule = module {

    viewModel {
        SplashViewModel(androidApplication())
    }

}
