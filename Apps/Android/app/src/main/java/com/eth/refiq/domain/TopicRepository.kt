package com.eth.refiq.domain

interface TopicRepository {
    suspend fun searchTopic(query: String): List<Topic>?

    suspend fun createTopic(name: String, bio: String, rules: List<String>): String
}