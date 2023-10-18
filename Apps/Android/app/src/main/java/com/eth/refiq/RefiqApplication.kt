package com.eth.refiq
import android.app.Application
import com.eth.refiq.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class RefiqApplication : Application() {

    override fun onCreate() {
        super.onCreate()
       // initWalletConnect()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@RefiqApplication)
            modules(appModule)
        }
    }

}