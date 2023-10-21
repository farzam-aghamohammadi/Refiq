package com.eth.refiq.data

import CoroutineDispatcherProvider
import android.content.Context
import com.eth.refiq.data.contract.Topics
import com.eth.refiq.domain.LocalDataStorage
import com.eth.refiq.domain.Web3Repository
import kotlinx.coroutines.withContext
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.TransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.utils.Convert
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

    val contractAddress = "0xAC0Ac07D405b5435B1bc5B305e2cCeB191F643d4"
    override suspend fun createTopic(name: String, cid: String) {
        val transactionManager: TransactionManager = RawTransactionManager(
            web3, credential, CHAIN_ID
        )


        val topic = Topics.load(
            contractAddress, web3, transactionManager,
            StaticGasProvider(DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT)
        )

        val createTopic = topic.createTopic(name, cid)
        val transactionReceipt = createTopic.send()
        println("$name")
        println("${transactionReceipt.logs}")
        println("${transactionReceipt.blockNumber}")
        println("${transactionReceipt.gasUsed}")
        println(transactionReceipt.transactionHash)

        println(transactionReceipt.revertReason)


    }

    override suspend fun deleteContent(id: String) {
        val transactionManager: TransactionManager = RawTransactionManager(
            web3, credential, CHAIN_ID
        )


        val topic = Topics.load(
            contractAddress, web3, transactionManager,
            StaticGasProvider(DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT)
        )

        val removeTopic = topic.removeContent(id.toBigInteger())
        val transactionReceipt = removeTopic.send()
    }

    override suspend fun createPost(topicId: String, cid: String) {

        val transactionManager: TransactionManager = RawTransactionManager(
            web3, credential, CHAIN_ID
        )


        val topic = Topics.load(
            contractAddress, web3, transactionManager,
            StaticGasProvider(DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT)
        )

        val createPost = topic.createPost(topicId.toBigInteger(), cid)
        val transactionReceipt = createPost.send()
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
        web3?.web3ClientVersion()?.sendAsync()?.get()
    }

    override suspend fun getBalance(): String {

        return withContext(coroutineDispatcherProvider.ioDispatcher()) {
           val amount= web3?.ethGetBalance(
                credential?.address,
                DefaultBlockParameterName.LATEST
            )?.send()?.balance.toString()
          return@withContext  Convert.fromWei(amount, Convert.Unit.ETHER).toString()
        }

    }

    override suspend fun getAddress(): String {
        return credential!!.address
    }

    override suspend fun saveWallet() {
        localDataStorage.saveValue(IS_WALLET_CREATED, true)
    }

    override suspend fun createComment(parentId: String, cid: String) {
        val transactionManager: TransactionManager = RawTransactionManager(
            web3, credential, CHAIN_ID
        )


        val topic = Topics.load(
            contractAddress, web3, transactionManager,
            StaticGasProvider(DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT)
        )

        val createComment = topic.createComment(parentId.toBigInteger(), cid)
        val transactionReceipt = createComment.send()
        println("${transactionReceipt.gasUsed}")
    }

    companion object {
        private const val IS_WALLET_CREATED = "hasWallet"
        private const val PASSWORD = "password"
        private const val WALLET_FILE_NAME = "wallet_file_name"
        private const val CHAIN_ID: Long = 534351
    }
}