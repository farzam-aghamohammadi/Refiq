package com.eth.refiq.data

import GetTopicByNameQuery
import com.apollographql.apollo3.ApolloClient
import com.eth.refiq.data.dto.SaveTopicDto
import com.eth.refiq.data.dto.TopicInfoDto
import com.eth.refiq.domain.Topic
import com.eth.refiq.domain.TopicRepository


class RemoteTopicRepository constructor(
    private val api: Api,
    private val apolloClient: ApolloClient
) : TopicRepository {
    override suspend fun searchTopic(query: String): List<Topic>? {
        val response = apolloClient.query(GetTopicByNameQuery(query)).execute()
        return response.data?.topics?.map {
            Topic(it.id, it.name, it.owner, it.infoCid, it.moderators)
        }
    }

    override suspend fun createTopic(name: String, bio: String, rules: List<String>): String {
        val saveTopicDto = SaveTopicDto(TopicInfoDto(bio, null, null, rules))
        return api.uploadTopicInfo(saveTopicDto).cid
    }


}