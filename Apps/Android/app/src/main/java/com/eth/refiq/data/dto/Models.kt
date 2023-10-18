package com.eth.refiq.data.dto

import com.google.gson.annotations.SerializedName


data class SaveTopicDto(
    @SerializedName("info")
    val info:TopicInfoDto
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

data class UploadApiResponse(
    @SerializedName("cid")
    val cid:String
)



