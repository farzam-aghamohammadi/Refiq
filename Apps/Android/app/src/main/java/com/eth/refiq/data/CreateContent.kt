package com.eth.refiq.data

import com.eth.refiq.data.dto.ContentDto
import com.eth.refiq.domain.ContentType
import com.eth.refiq.domain.CreateContentRepository
import com.eth.refiq.domain.Web3Repository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class CreateContent constructor(private val api: Api, private val web3Repository: Web3Repository) :
    CreateContentRepository {
    override suspend fun createContent(
        text: String,
        imagePath: String?,
        videoPath: String?,
        contentType: ContentType,
        parentId: String
    ) {
        var videoCid: String? = null
        var imageCid: String? = null
        videoPath?.let {
            videoCid = api.uploadFile(mapToMultiPartBody(File((videoPath.toString())))).cid
        }
        imagePath?.let {
            imageCid = api.uploadFile(mapToMultiPartBody(File(imagePath.toString()))).cid
        }
        val saveContentDto = ContentDto(text, imageCid, videoCid)
        val contentCid = api.uploadContent(saveContentDto).cid
        if (contentType == ContentType.POST) {
            web3Repository.createPost(parentId, contentCid)
        } else {
            web3Repository.createComment(parentId, contentCid)
        }
    }

    private fun mapToMultiPartBody(file: File, fileName: String? = null): MultipartBody.Part {
        val requestFile = file.asRequestBody("".toMediaTypeOrNull()).apply { }
        return MultipartBody.Part.createFormData("file", fileName ?: file.name, requestFile)
    }

}