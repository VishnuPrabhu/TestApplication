package com.vishnu.testapplication

import android.app.Application
import com.vishnu.testapplication.di.*
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

//@HiltAndroidApp
class MobileBankApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MobileBankApplication)
            modules(launchModule, loginModule, homeModule)
            modules(repositoryModule, databaseModule)
            modules(coroutinesModule)
        }
    }
}