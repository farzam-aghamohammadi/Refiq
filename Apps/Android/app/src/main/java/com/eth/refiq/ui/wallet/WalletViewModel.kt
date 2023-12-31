package com.eth.refiq.ui.wallet

import CoroutineDispatcherProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eth.refiq.domain.Web3Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WalletViewModel constructor(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val web3Repository: Web3Repository,
) : ViewModel() {

    private val _mnemonicLiveData = MutableLiveData<String>()

    val mnemonicLiveData: LiveData<String> = _mnemonicLiveData

    private val _walletConnected = MutableLiveData(false)

    val walletConnected: LiveData<Boolean> = _walletConnected

    private val _walletInfo = MutableLiveData<WalletInfo>()

    val walletInfo: LiveData<WalletInfo> = _walletInfo


    private val _walletImported = MutableLiveData(false)

    val walletImported: LiveData<Boolean> = _walletImported
    fun init() {}

    init {
        loadWallet()
    }

    fun loadWallet() {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    web3Repository.isWalletCreated()
                }
            }.fold({
                _walletConnected.postValue(it)
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    if (it) {
                        onWalletCreated()
                    }
                }
            }, {
                it.printStackTrace()
            })
        }
    }

    fun createWallet(password: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    web3Repository.createWallet(password)
                }
            }.fold({
                _mnemonicLiveData.value = it
            }, {
                it.printStackTrace()
            })
        }
    }

    fun saveWallet() {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    web3Repository.saveWallet()
                }
            }.fold({
                onWalletCreated()
            }, {
                it.printStackTrace()
            })
        }
    }

    private fun onWalletCreated() {
        _walletConnected.postValue(true)
        loadCredential()
    }

    private fun loadCredential() {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    //TODO should get password from user
                    web3Repository.loadWallet("")
                    Pair(web3Repository.getAddress(), web3Repository.getBalance())
                }
            }.fold({
                _walletInfo.value = WalletInfo(it.first, it.second)
            }, {
                it.printStackTrace()
            })
        }
    }

    fun importWallet(secretPhrase: String, password: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    web3Repository.importWallet(secretPhrase, password)
                }
            }.fold({
                _walletImported.postValue(true)
            }, {

                it.printStackTrace()
            })
        }
    }
}

data class WalletInfo(val address: String, val balance: String)