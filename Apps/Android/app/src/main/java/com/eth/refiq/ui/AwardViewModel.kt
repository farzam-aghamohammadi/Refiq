package com.eth.refiq.ui

import CoroutineDispatcherProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eth.refiq.domain.Web3Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AwardViewModel constructor(
    private val web3Repository: Web3Repository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    fun init(){}
    fun sendGold(weiAmount: String, contentId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    web3Repository.awardContent(weiAmount, contentId)
                }
            }.fold({}, {
                it.printStackTrace()
            })
        }
    }
}