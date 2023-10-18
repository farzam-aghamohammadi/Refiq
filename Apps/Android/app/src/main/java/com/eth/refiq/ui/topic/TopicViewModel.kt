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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopicViewModel constructor(
    private val topic: Topic,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val postRepository: PostRepository
) : ViewModel() {
    init {
        getPosts(topic)
    }

    val postsLiveData: LiveData<List<Post>>
        get() = _postsLiveData

    private val _postsLiveData =
        MutableLiveData<List<Post>>()

   private fun getPosts(topic: Topic) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    postRepository.getPosts(topic.id)
                }
            }.fold({
                _postsLiveData.value = it
            }, {
                it.printStackTrace()
            })
        }
    }

    fun addPost() {

    }

    fun createTopic() {

    }

}