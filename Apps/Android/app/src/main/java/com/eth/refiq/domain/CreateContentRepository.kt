package com.eth.refiq.domain

interface CreateContentRepository {
    suspend fun createContent(
        text: String,
        imagePath: String?,
        videoPath: String?,
        contentType: ContentType,
        parentId: String
    )
}