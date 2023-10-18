package com.eth.refiq.data

import com.eth.refiq.data.dto.SaveTopicDto
import com.eth.refiq.data.dto.TopicInfoDto
import com.eth.refiq.domain.Topic
import com.eth.refiq.domain.TopicRepository


class RemoteTopicRepository constructor(private val api: Api) : TopicRepository {
    override fun searchTopic(query: String): List<Topic> {
        return listOf(Topic("31312", "Refiq guys", "", ""), Topic("31sds312", "OmgCommu", "", ""))
    }

    override suspend fun createTopic(name: String, bio: String, rules: List<String>): String {
        val saveTopicDto = SaveTopicDto(TopicInfoDto(bio, name, null, rules))
        return api.upload(saveTopicDto).cid
    }


}