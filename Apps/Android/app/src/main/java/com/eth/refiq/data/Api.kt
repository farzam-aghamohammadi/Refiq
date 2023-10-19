package com.eth.refiq.data

import com.eth.refiq.data.dto.ContentDto
import com.eth.refiq.data.dto.SaveTopicDto
import com.eth.refiq.data.dto.UploadApiResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Api {

    @POST("/upload")
    suspend fun uploadTopicInfo(
        @Body
        info: SaveTopicDto
    ): UploadApiResponse

    @POST("/upload")
    suspend fun uploadContent(
        @Body
        info: ContentDto
    ): UploadApiResponse


    @Multipart
    @POST("/upload")
    suspend fun uploadFile(
        @Part
        file: MultipartBody.Part,
    ): UploadApiResponse
}