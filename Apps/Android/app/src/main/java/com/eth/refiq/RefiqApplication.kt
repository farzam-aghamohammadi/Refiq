package com.eth.refiq

import android.app.Application
import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient
import com.walletconnect.android.relay.ConnectionType
import com.walletconnect.sign.client.Sign
import com.walletconnect.sign.client.SignClient


class RefiqApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val projectId = "" // Get Project ID at https://cloud.walletconnect.com/
        val relayUrl = "relay.walletconnect.com"
        val serverUrl = "wss://$relayUrl?projectId=${projectId}"

        val appMetaData = Core.Model.AppMetaData(
            name = "Refiq Dapp",
            description = "Refiq Dapp Implementation",
            url = "refiq.com",
            icons = listOf("https://gblobscdn.gitbook.com/spaces%2F-LJJeCjcLrr53DcT1Ml7%2Favatar.png?alt=media"),
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