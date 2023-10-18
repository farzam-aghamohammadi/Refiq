package com.eth.refiq.data

import com.eth.refiq.data.dto.SaveTopicDto
import com.eth.refiq.data.dto.UploadApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("/upload")
    suspend fun upload(
        @Body
        info: SaveTopicDto
    ): UploadApiResponse

}