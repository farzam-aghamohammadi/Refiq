package com.eth.refiq.ui.add.topic

import CoroutineDispatcherProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eth.refiq.domain.TopicRepository
import com.eth.refiq.domain.Web3Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateTopicViewModel constructor(
    private val topicRepository: TopicRepository,
    private val web3JRepository: Web3Repository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    val creatingTopic: LiveData<Boolean>
        get() = _creatingTopic

    private val _creatingTopic =
        MutableLiveData<Boolean>(false)

    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _errorMessage =
        MutableLiveData<String>()

    fun createTopic(name: String, bio: String, rule: String) {
        viewModelScope.launch {
            _creatingTopic.value = true
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    val cid = topicRepository.createTopic(name, bio, listOf(rule))
                    web3JRepository.createTopic(name, cid)
                }
            }.fold({
                println("$it")
                _creatingTopic.value = false

            }, {
                _creatingTopic.value = false
                _errorMessage.value = it.message
                it.printStackTrace()
            })
        }
    }

}