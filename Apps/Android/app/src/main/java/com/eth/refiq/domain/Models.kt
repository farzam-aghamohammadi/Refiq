package com.eth.refiq.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Topic(val id: String, val name: String, val owner: String, val infoCid: String) :
    Serializable

enum class ContentType : Serializable {
    POST,
    COMMENT
}

data class Post(
    val id: String,
    val walletAddress: String,
    val postType: PostType,
    val text: String
)


sealed class PostType {
    data object Text : PostType()
    data class Image(val uri: String) : PostType()
    data class Video(val uri: String) : PostType()

}

data class Content(
    val id: String,
    val walletAddress: String,
    val postType: PostType,
    val text: String
)

data class ContentDetail(val mainContent: Content, val contents: List<Content>)