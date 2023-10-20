package com.eth.refiq.domain

interface PostRepository {
    suspend fun getPostsByTopicId(topicId:String) : List<Post>
}