package com.eth.refiq.data

import CoroutineDispatcherProvider
import android.content.Context
import com.eth.refiq.domain.LocalDataStorage
import com.eth.refiq.domain.Web3Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import java.io.File

class Web3JRepository constructor(
    private val context: Context,
    private val localDataStorage: LocalDataStorage,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : Web3Repository {
    private var web3: Web3j? = null

    private var credential: Credentials? = null

    init {
        web3 =
            Web3j.build(HttpService("https://1rpc.io/scroll/sepolia"))
    }

    private fun getWalletDirectory(): String {
        return context.filesDir.toString() + context.packageName
    }

    override suspend fun createWallet(password: String): String {
        val file = File(getWalletDirectory()) // the etherium wallet location

        //create the directory if it does not exist
        if (!file.mkdirs()) {
            file.mkdirs()
        }
        val bip39Wallet = WalletUtils.generateBip39Wallet(password, file)
        localDataStorage.saveValue(PASSWORD, password)
        localDataStorage.saveValue(WALLET_FILE_NAME, bip39Wallet.filename)

        println("fileName ${bip39Wallet.filename} ${file.name}")
        return bip39Wallet.mnemonic
    }

    override suspend fun importWallet(secretPhrase: String, password: String) {
        val file = File(getWalletDirectory()) // the etherium wallet location

        //create the directory if it does not exist
        if (!file.mkdirs()) {
            file.mkdirs()
        }
        val bip39Wallet = WalletUtils.generateBip39WalletFromMnemonic(password, secretPhrase, file)
        localDataStorage.saveValue(WALLET_FILE_NAME, bip39Wallet.filename)
        localDataStorage.saveValue(PASSWORD, password)

    }

    override suspend fun isWalletCreated(): Boolean {
        return localDataStorage.getBooleanValue(IS_WALLET_CREATED)
    }

    override suspend fun loadWallet(password: String) {
        val walletFileName = localDataStorage.getValue(WALLET_FILE_NAME)
        val file = File(getWalletDirectory() + "/" + walletFileName)
        credential = WalletUtils.loadCredentials(
            localDataStorage.getValue(PASSWORD),
            file
        )
    }

    override suspend fun getBalance(): String {
        return withContext(coroutineDispatcherProvider.ioDispatcher()) {
            web3?.ethGetBalance(
                credential?.address,
                DefaultBlockParameterName.LATEST
            )?.sendAsync()
                ?.get()?.balance.toString()
        }

    }

    override suspend fun getAddress(): String {
        return credential!!.address
    }

    override suspend fun saveWallet() {
        localDataStorage.saveValue(IS_WALLET_CREATED, true)
    }


    companion object {
        private const val IS_WALLET_CREATED = "hasWallet"
        private const val PASSWORD = "password"
        private const val WALLET_FILE_NAME = "wallet_file_name"
    }
}