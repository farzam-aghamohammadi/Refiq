package com.eth.refiq

import android.app.Application
import com.eth.refiq.di.appModule
import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient
import com.walletconnect.android.relay.ConnectionType
import com.walletconnect.sign.client.Sign
import com.walletconnect.sign.client.SignClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class RefiqApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initWalletConnect()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@RefiqApplication)
            modules(appModule)
        }
    }

    private fun initWalletConnect() {
        val projectId =
            "" // Get Project ID at https://cloud.walletconnect.com/
        val relayUrl = "relay.walletconnect.com"
        val serverUrl = "wss://$relayUrl?projectId=${projectId}"

        val appMetaData = Core.Model.AppMetaData(
            name = "Refiq Dapp",
            description = "Refiq Dapp Implementation",
            url = "refiq.com",
            icons = listOf(),
            redirect = "refiq-dapp://request"
        )

        CoreClient.initialize(
            relayServerUrl = serverUrl,
            connectionType = ConnectionType.AUTOMATIC,
            application = this,
            metaData = appMetaData,
        ) {
            println("Error CoreClient.initialize $it")
        }

        SignClient.initialize(
            init = Sign.Params.Init(CoreClient),
            onSuccess = {
                println("onSuccess: SignClient.initialize")
            },
            onError = { error ->
                println("Error SignClient.initialize $error")
                return@initialize
            }
        )
    }
}