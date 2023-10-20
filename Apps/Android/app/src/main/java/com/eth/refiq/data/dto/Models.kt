package com.eth.refiq.data.dto

import com.google.gson.annotations.SerializedName


data class SaveTopicDto(
    @SerializedName("info")
    val info: TopicInfoDto
)

data class TopicInfoDto(
    @SerializedName("bio")
    val bio: String,
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("cover")
    val cover: String?,
    @SerializedName("rules")
    val rules: List<String>
)

data class ContentDto(
    @SerializedName("text")
    val text: String,
    @SerializedName("imageCid")
    val imageCid: String?,
    @SerializedName("videoCid")
    val videoCid: String?,
)

data class ContentResponseDto(
    val id: String,
    val contentCid: String,
    val author: String,
    val comments: List<String>
)

data class UploadApiResponse(
    @SerializedName("cid")
    val cid: String
)



