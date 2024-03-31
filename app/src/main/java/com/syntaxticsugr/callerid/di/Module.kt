package com.syntaxticsugr.callerid.di

import com.syntaxticsugr.callerid.datastore.DataStorePref
import com.syntaxticsugr.callerid.viewmodel.HomeViewModel
import com.syntaxticsugr.callerid.viewmodel.LoginViewModel
import com.syntaxticsugr.callerid.viewmodel.PermissionsViewModel
import com.syntaxticsugr.callerid.viewmodel.SearchBarViewModel
import com.syntaxticsugr.callerid.viewmodel.SplashViewModel
import com.syntaxticsugr.callerid.viewmodel.VerifyViewModel
import com.syntaxticsugr.callerid.viewmodel.WelcomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val calleridModule = module {

    single<DataStorePref> {
        DataStorePref(context = androidContext())
    }

    viewModel<SplashViewModel> {
        SplashViewModel(get())
    }

    viewModel<WelcomeViewModel> {
        WelcomeViewModel(get())
    }

    viewModel<PermissionsViewModel> {
        PermissionsViewModel(androidApplication())
    }

    viewModel<LoginViewModel> {
        LoginViewModel(get())
    }

    viewModel<VerifyViewModel> {
        VerifyViewModel(androidApplication(), get())
    }

    viewModel<HomeViewModel> {
        HomeViewModel(androidApplication())
    }

    viewModel<SearchBarViewModel> {
        SearchBarViewModel(androidApplication())
    }

}
