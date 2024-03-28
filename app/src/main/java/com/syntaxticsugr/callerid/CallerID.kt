package com.syntaxticsugr.callerid

import android.app.Application
import com.syntaxticsugr.callerid.di.calleridModule
import com.syntaxticsugr.callerid.realm.objects.PhoneNumberInfo
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CallerID : Application() {

    companion object {
        lateinit var realm: Realm
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CallerID)
            modules(calleridModule)
        }

        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(PhoneNumberInfo::class)
            )
        )
    }

}
