package com.eth.refiq.data

import com.eth.refiq.domain.Post
import com.eth.refiq.domain.PostRepository

class RemotePostRepository : PostRepository {
    override suspend fun getPosts(topicId: String): List<Post> {
        return listOf()
    }
}