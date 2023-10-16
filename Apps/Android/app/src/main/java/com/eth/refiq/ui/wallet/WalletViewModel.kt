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
    private val web3Repository: Web3Repository
) : ViewModel() {

    private val _mnemonicLiveData = MutableLiveData<String>()

    val mnemonicLiveData: LiveData<String> = _mnemonicLiveData

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
}