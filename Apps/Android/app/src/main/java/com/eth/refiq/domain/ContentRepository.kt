package com.eth.refiq.domain

interface ContentRepository {
    suspend fun fetchPostContentDetail(
        id: String,
        first: Int,
        skip: Int
    ): Pair<ContentDetail, List<String>>?

    suspend fun fetchCommentContentDetail(id: String, first: Int, skip: Int): ContentDetail?

    suspend fun deleteContent(id: String)
}