package com.eth.refiq.domain

interface PostRepository {
    suspend fun getPosts(topicId:String) : List<Post>
}