package com.eth.refiq.ui.searchtopic

import CoroutineDispatcherProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eth.refiq.domain.Topic
import com.eth.refiq.domain.TopicRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchTopicViewModel constructor(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val topicRepository: TopicRepository

) : ViewModel() {
    init {
        onSearchTopicChanged("")
    }
    val topicsLiveData: LiveData<List<Topic>>
        get() = _topicsLiveData

    private val _topicsLiveData =
        MutableLiveData<List<Topic>>()

    private var searchedJob: Job? = null

    fun onSearchTopicChanged(query: String) {
       searchedJob?.cancel()
        searchedJob=viewModelScope.launch {
            runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()){
                    topicRepository.searchTopic(query)
                }
            }.fold({
                it?.let {
                    _topicsLiveData.value=it
                }
            },{
                println(it)
            })
        }
    }



}