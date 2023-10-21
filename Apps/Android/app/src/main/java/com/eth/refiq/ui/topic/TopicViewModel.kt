package com.eth.refiq.ui.topic

import CoroutineDispatcherProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eth.refiq.data.Web3JRepository
import com.eth.refiq.domain.Post
import com.eth.refiq.domain.PostRepository
import com.eth.refiq.domain.Topic
import com.eth.refiq.domain.TopicRepository
import com.eth.refiq.domain.Web3Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopicViewModel constructor(
    private val topic: Topic,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val postRepository: PostRepository,
    private val web3Repository: Web3Repository
) : ViewModel() {
    val isOwner: LiveData<Boolean>
        get() = _isOwner

    private val _isOwner =
        MutableLiveData<Boolean>(false)
    val postsLiveData: LiveData<List<Post>>
        get() = _postsLiveData

    private val _postsLiveData =
        MutableLiveData<List<Post>>()

    init {
            getPosts(topic)
            isOwner()
    }

    private fun isOwner() {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    web3Repository.getAddress()
                }
            }.fold({
                _isOwner.postValue(topic.owner == it)
            }, {
                it.printStackTrace()
            })
        }
    }




    private fun getPosts(topic: Topic) {
        println("getPostss")
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    postRepository.getPostsByTopicId(topic.id)
                }
            }.fold({
                it.forEach {
                    println("${it.text} : ${it.postType}")
                }
                _postsLiveData.value = it
            }, {
                it.printStackTrace()
            })
        }
    }

    fun refresh() {
        topic?.let {
            getPosts(topic)
        }
    }


}