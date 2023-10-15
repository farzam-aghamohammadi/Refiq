package com.eth.refiq.domain

interface TopicRepository {
    fun searchTopic(query: String): List<Topic>

}