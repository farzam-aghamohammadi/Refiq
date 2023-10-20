package com.eth.refiq.domain

import java.io.Serializable


data class Topic(val id: String, val name: String, val owner: String, val infoCid: String) :
    Serializable

enum class ContentType : Serializable {
    POST,
    COMMENT
}

data class Post(
    val walletAddress: String,
    val postType: PostType,
    val text: String,
    val comments: List<Comment>
)

data class Comment(val string: String)
sealed class PostType {
    data object Text : PostType()
    data class Image(val uri: String) : PostType()
    data class Video(val uri: String) : PostType()

}