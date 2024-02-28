package com.syntaxticsugr.callerid

import android.app.Application
import com.syntaxticsugr.callerid.di.calleridModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CallerID : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CallerID)
            modules(calleridModule)
        }
    }

}
