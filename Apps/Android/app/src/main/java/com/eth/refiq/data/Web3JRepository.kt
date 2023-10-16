package com.eth.refiq.data

import android.content.Context
import com.eth.refiq.domain.Web3Repository
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import java.io.File

class Web3JRepository constructor(private val context: Context) : Web3Repository {
    var web3: Web3j? = null

    init {
        web3 =
            Web3j.build(HttpService("https://1rpc.io/scroll/sepolia"))

    }

    override suspend fun createWallet(password: String): String {
        val file = File(context.filesDir.toString()) // the etherium wallet location

        //create the directory if it does not exist
        if (!file.mkdirs()) {
            file.mkdirs()
        }
        val bip39Wallet = WalletUtils.generateBip39Wallet(password, file)
        return bip39Wallet.mnemonic
    }
}