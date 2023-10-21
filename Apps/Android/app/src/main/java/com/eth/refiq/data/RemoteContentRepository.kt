package com.eth.refiq.data

import GetCommentDetailsQuery
import GetPostDetailsQuery
import android.view.ContentInfo
import com.apollographql.apollo3.ApolloClient
import com.eth.refiq.data.dto.ContentDto
import com.eth.refiq.domain.Content
import com.eth.refiq.domain.ContentDetail
import com.eth.refiq.domain.ContentRepository
import com.eth.refiq.domain.PostType
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL

class RemoteContentRepository(private val apolloClient: ApolloClient) : ContentRepository {
    override suspend fun fetchPostContentDetail(
        id: String,
        first: Int,
        skip: Int
    ): Pair<ContentDetail, List<String>>? {
        val response = apolloClient.query(GetPostDetailsQuery(id, first, skip)).execute()
        val moderators = mutableListOf<String>()
        println("first ${response.data?.post}")
        response.data?.post?.let {
            println("dsdwasdf $first : $it")
        }
        return response.data?.post?.let { post ->
            val mainContentDto = mapToContentDto(post.contentCid)
            val contents = mutableListOf<Content>()
            val mainContent = Content(
                post.id,
                post.author,
                mapToPostType(mainContentDto),
                mainContentDto.text
            )

            post.comments.forEach { comment ->

                val contentDto = mapToContentDto(comment.contentCid)
                contents.add(
                    Content(
                        comment.id,
                        comment.author,
                        mapToPostType(contentDto),
                        contentDto.text
                    )
                )
            }
            post.topic.moderators.forEach {
                moderators.add(it)
            }
            moderators.add(post.topic.owner)

            return@let Pair(ContentDetail(mainContent, contents), moderators)

        }

    }

    override suspend fun fetchCommentContentDetail(
        id: String,
        first: Int,
        skip: Int
    ): ContentDetail? {
        val response = apolloClient.query(GetCommentDetailsQuery(id, first, skip)).execute()
        val contents = mutableListOf<Content>()
        return response.data?.comment?.let { comment ->
            val mainContentDto = mapToContentDto(comment.contentCid)
            val mainContent = Content(
                comment.id,
                comment.author,
                mapToPostType(mainContentDto),
                mainContentDto.text
            )

            comment.comments.forEach { comments ->
                val contentDto = mapToContentDto(comments.contentCid)
                contents.add(
                    Content(
                        comments.id,
                        comments.author,
                        mapToPostType(contentDto),
                        contentDto.text
                    )
                )
            }

            return@let ContentDetail(mainContent, contents)
        }


    }

    override suspend fun deleteContent(id: String) {
        TODO("Not yet implemented")
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