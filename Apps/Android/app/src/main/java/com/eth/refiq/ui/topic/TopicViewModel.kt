package com.eth.refiq.ui.topic

import CoroutineDispatcherProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eth.refiq.domain.Post
import com.eth.refiq.domain.PostRepository
import com.eth.refiq.domain.Topic
import com.eth.refiq.domain.TopicRepository
import com.eth.refiq.domain.Web3Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopicViewModel constructor(
    private val topic: Topic?,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val postRepository: PostRepository,
    private val topicRepository: TopicRepository,
    private val web3JRepository: Web3Repository,
) : ViewModel() {
    init {
        println("initt")
        topic?.let {
            getPosts(topic)
        }
    }

    val postsLiveData: LiveData<List<Post>>
        get() = _postsLiveData

    private val _postsLiveData =
        MutableLiveData<List<Post>>()

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

    fun addPost() {

    }

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