package com.eth.refiq.ui.contentdetail

import CoroutineDispatcherProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eth.refiq.domain.Content
import com.eth.refiq.domain.ContentDetail
import com.eth.refiq.domain.ContentRepository
import com.eth.refiq.domain.Web3Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class ContentDetailViewModel constructor(
    private val moderators: ArrayList<String>,
    private val contentDetailInfo: ContentDetailInfo,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val contentRepository: ContentRepository,
    private val web3Repository: Web3Repository
) :
    ViewModel() {

    val contentsLiveData: LiveData<List<Content>>
        get() = _contentsLiveData

    private val _contentsLiveData =
        MutableLiveData<List<Content>>()

    val mainContentLiveData: LiveData<Content>
        get() = _mainContentLiveData

    private val _mainContentLiveData =
        MutableLiveData<Content>()

    val canDelete: LiveData<Boolean>
        get() = _canDelete

    private val _canDelete =
        MutableLiveData<Boolean>(false)

    init {
        getWalletAddress()
        fetchContentDetail()
    }

    private var walletAddress: String? = null


    private fun getWalletAddress() {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    web3Repository.getAddress()
                }
            }.fold({
                walletAddress = it
            }, {
                it.printStackTrace()
            })
        }
    }

    private fun fetchContentDetail() {
        if (contentDetailInfo is ContentDetailInfo.PostDetail) {
            fetchPostDetail(contentDetailInfo.parentId)
        } else if (contentDetailInfo is ContentDetailInfo.CommentDetail) {
            fetchCommentDetail(contentDetailInfo.parentId)
        }
    }

    private fun fetchPostDetail(id: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    contentRepository.fetchPostContentDetail(id, 50, 0)
                }
            }.fold({ pair ->
                pair?.let {
                    println("dasdasda ${pair.first} ${pair.second}")
                    val contentDetail = pair.first
                    _contentsLiveData.value = contentDetail.contents
                    _mainContentLiveData.value = contentDetail.mainContent
                    moderators.addAll(pair.second)
                    canDelete(contentDetail)
                }
            }, {
                it.printStackTrace()
            })
        }
    }

    private fun fetchCommentDetail(id: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(coroutineDispatcherProvider.ioDispatcher()) {
                    contentRepository.fetchCommentContentDetail(id, 50, 0)
                }
            }.fold({

                it?.let { contentDetail ->
                    _mainContentLiveData.value = contentDetail.mainContent
                    _contentsLiveData.value = contentDetail.contents
                    canDelete(contentDetail)
                }

            }, {
                it.printStackTrace()
            })
        }
    }

    private fun canDelete(contentDetail: ContentDetail) {
        walletAddress?.let { currentAddress ->
            if (currentAddress == contentDetail.mainContent.walletAddress) {
                _canDelete.value = true
            } else {
                moderators.forEach {
                    if (it == currentAddress) {
                        _canDelete.value = true
                    }
                }
            }
        }
    }

    fun deleteContent() {
        _mainContentLiveData.value?.let {
            content ->
            viewModelScope.launch {
                kotlin.runCatching {
                    withContext(coroutineDispatcherProvider.ioDispatcher()){
                        web3Repository.deleteContent(content.id)
                    }
                }.fold({

                },{
                    it.printStackTrace()
                })
            }
        }
    }
}

sealed class ContentDetailInfo : Serializable {
    data class PostDetail(val parentId: String, val content: Content) : ContentDetailInfo()
    data class CommentDetail(val parentId: String, val content: Content) : ContentDetailInfo()

}