package com.eth.refiq.ui.ownerpanel

import CoroutineDispatcherProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eth.refiq.domain.Topic
import com.eth.refiq.domain.Web3Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopicOwnerViewModel constructor(
    private val topic: Topic,
    private val web3Repository: Web3Repository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _moderators = MutableLiveData<List<String>>(topic.moderators.toMutableList())

    val moderators: LiveData<List<String>> = _moderators
    fun addModerator(address: String) {

        topic.moderators.add(address)
        _moderators.value = topic.moderators
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    web3Repository.addModerator(address, topic.id)
                }
            }.fold({

            }, {
                it.printStackTrace()
            })
        }
    }

    fun removeModerator(address: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    web3Repository.removeModerator(address, topic.id)
                }
            }.fold({
                topic.moderators.remove(address)
                _moderators.value = topic.moderators
            }, {
                it.printStackTrace()
            })
        }
    }
}