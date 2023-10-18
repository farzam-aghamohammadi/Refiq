package com.eth.refiq.domain

interface TopicRepository {
    fun searchTopic(query: String): List<Topic>

    fun createTopic(name:String, bio:String,rules:String,avatar:String)
}