package com.eth.refiq.data

import com.eth.refiq.domain.Topic
import com.eth.refiq.domain.TopicRepository

class RemoteTopicRepository : TopicRepository {
    override fun searchTopic(query: String): List<Topic> {
        return listOf(Topic("31312", "Refiq guys", "", ""), Topic("31sds312", "OmgCommu", "", ""))
    }

    override fun createTopic(name: String, bio: String, rules: String, avatar: String) {
        TODO("Not yet implemented")
    }
}