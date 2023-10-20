package com.eth.refiq.data

import GetPostsByTopicIdQuery
import com.apollographql.apollo3.ApolloClient
import com.eth.refiq.data.dto.ContentDto
import com.eth.refiq.data.dto.ContentResponseDto
import com.eth.refiq.domain.Post
import com.eth.refiq.domain.PostRepository
import com.eth.refiq.domain.PostType
import com.google.gson.Gson

import java.io.InputStreamReader

import java.net.URL


class RemotePostRepository(private val apolloClient: ApolloClient) : PostRepository {
    override suspend fun getPostsByTopicId(topicId: String): List<Post> {
        val response = apolloClient.query(GetPostsByTopicIdQuery(topicId)).execute()
        val listOfContentResponseDto = response.data?.topic?.posts?.map {
            ContentResponseDto(it.id, it.contentCid, it.author, it.comments.map { it.id })
        }
        val posts = mutableListOf<Post>()
        listOfContentResponseDto?.forEach {
            try {
                val contentDto = mapToContentDto(cid = it.contentCid)
                posts.add(
                    Post(
                        it.id,
                        it.author,
                        mapToPostType(contentDto),
                        contentDto.text,
                        it.comments
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return posts
    }

    private fun mapToContentDto(cid: String): ContentDto {
        val url = URL(getCidLoadableURL(cid))
        val reader = InputStreamReader(url.openStream())
        return Gson().fromJson(reader, ContentDto::class.java)
    }

    private fun mapToPostType(contentDto: ContentDto): PostType {
        if (contentDto.videoCid != null) {
            return PostType.Video(getCidLoadableURL(contentDto.videoCid))
        } else if (contentDto.imageCid != null) {
            return PostType.Image(getCidLoadableURL(contentDto.imageCid))
        }
        return PostType.Text
    }

    private fun getCidLoadableURL(cid: String): String {
        return "https://$cid.ipfs.w3s.link/"
    }
}