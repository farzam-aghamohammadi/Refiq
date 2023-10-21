package com.eth.refiq.ui.contentdetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eth.refiq.databinding.ItemContentHeaderBinding
import com.eth.refiq.databinding.ItemPostImageBinding
import com.eth.refiq.databinding.ItemPostTextBinding
import com.eth.refiq.databinding.ItemPostVideoBinding
import com.eth.refiq.domain.Content
import com.eth.refiq.domain.PostType

class ContentAdapter constructor(
    private val onContentClicked: ((Content) -> Unit),
    private val onCommentClicked: ((Content) -> Unit),
    private val onGoldClicked: ((Content) -> Unit),
) :
    RecyclerView.Adapter<PostItemViewHolder>() {
    private val items = mutableListOf<Content>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        return when (viewType) {


            TEXT_POST_TYPE -> PostTextItemViewHolder(
                ItemPostTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )

            IMAGE_POST_TYPE -> PostImageItemViewHolder(
                ItemPostImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )

            VIDEO_POST_TYPE -> PostVideoItemViewHolder(
                ItemPostVideoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )

            else -> throw Throwable("Not found!!")
        }
    }

    fun updateAdapter(items: List<Content>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        holder.bind(items[position], onContentClicked, onCommentClicked, onGoldClicked)
    }

    override fun getItemViewType(position: Int): Int {

        return when (items[position].postType) {
            is PostType.Text -> TEXT_POST_TYPE
            is PostType.Image -> IMAGE_POST_TYPE
            is PostType.Video -> VIDEO_POST_TYPE
        }
    }

    override fun onViewRecycled(holder: PostItemViewHolder) {
        super.onViewRecycled(holder)
        (holder as? PostVideoItemViewHolder)?.release()
    }

    companion object {
        private const val TEXT_POST_TYPE = 1
        private const val VIDEO_POST_TYPE = 2
        private const val IMAGE_POST_TYPE = 3
    }
}


abstract class PostItemViewHolder(private val view: ViewGroup) : RecyclerView.ViewHolder(view) {
    abstract fun bind(
        post: Content,
        onContentClicked: (Content) -> Unit,
        onCommentClicked: (Content) -> Unit,
        onGoldClicked: (Content) -> Unit
    )
}

class HeaderItemViewHolder(private val binding: ItemContentHeaderBinding) :
    PostItemViewHolder(binding.root) {
    override fun bind(
        post: Content,
        onContentClicked: (Content) -> Unit,
        onCommentClicked: (Content) -> Unit,
        onGoldClicked: (Content) -> Unit
    ) {
      /*  binding.contentdetailHeadercontent.adapter =
            ContentAdapter({ }, onCommentClicked, onGoldClicked).apply {
                updateAdapter(
                    listOf(post)
                )
            }*/
    }
}

class PostTextItemViewHolder(private val binding: ItemPostTextBinding) :
    PostItemViewHolder(binding.root) {
    override fun bind(
        post: Content,
        onContentClicked: (Content) -> Unit,
        onCommentClicked: (Content) -> Unit,
        onGoldClicked: (Content) -> Unit
    ) {
        binding.itemcontentTextTop.itemcontentText.text = post.text
        binding.itemcontentTextTop.itemcontentAuthorid.text = post.walletAddress
        binding.itemcontentTextTop.itemcontentText.isGone = post.text.isEmpty()
        binding.itemcontentTextBottom.itemcontentCommentimage.setOnClickListener {
            onCommentClicked(post)
        }
        binding.itemcontentTextBottom.itemcontentGiftimage.setOnClickListener {
            onGoldClicked(post)
        }
        binding.root.setOnClickListener {
            onContentClicked(post)
        }
    }
}

class PostVideoItemViewHolder(private val binding: ItemPostVideoBinding) :
    PostItemViewHolder(binding.root) {
    private lateinit var post: Content
    override fun bind(
        post: Content,
        onContentClicked: (Content) -> Unit,
        onCommentClicked: (Content) -> Unit,
        onGoldClicked: (Content) -> Unit
    ) {
        this.post = post
        binding.itemVideoTop.itemcontentAuthorid.text = post.walletAddress
        binding.itemVideoTop.itemcontentText.text = post.text
        binding.itemVideoTop.itemcontentText.isGone = post.text.isEmpty()

        val postType = post.postType as PostType.Video
        Glide.with(binding.itemVideoContent).load(postType.uri).into(
            binding.itemVideothubnailContent
        )
        binding.itemVideoBottom.itemcontentCommentimage.setOnClickListener {
            onCommentClicked(post)
        }
        binding.itemVideoBottom.itemcontentGiftimage.setOnClickListener {
            onGoldClicked(post)
        }
        binding.root.setOnClickListener {
            onContentClicked(post)
        }
    }

    fun showVideo(exoPlayer: ExoPlayer) {
        binding.itemVideoContent.isVisible = true
        binding.itemVideothubnailContent.isGone = true
        binding.itemVideoContent.player = exoPlayer
        exoPlayer.setMediaItem(MediaItem.fromUri((post.postType as PostType.Video).uri))
        exoPlayer.play()

    }

    fun stopVideo() {
        binding.itemVideoContent.isGone = true
        binding.itemVideothubnailContent.isVisible = true
        binding.itemVideoContent.player?.stop()
        binding.itemVideoContent.player = null
    }

    fun release() {
        binding.itemVideoContent.player = null
    }
}

class PostImageItemViewHolder(private val binding: ItemPostImageBinding) :
    PostItemViewHolder(binding.root) {
    override fun bind(
        post: Content,
        onContentClicked: (Content) -> Unit,
        onCommentClicked: (Content) -> Unit,
        onGoldClicked: (Content) -> Unit
    ) {
        binding.itemImageTop.itemcontentAuthorid.text = post.walletAddress
        binding.itemImageTop.itemcontentText.text = post.text
        binding.itemImageTop.itemcontentText.isGone = post.text.isEmpty()
        Glide.with(binding.itemImageContent).load((post.postType as PostType.Image).uri)
            .into(binding.itemImageContent)
        binding.itemImageBottom.itemcontentCommentimage.setOnClickListener {
            onCommentClicked(post)
        }
        binding.itemImageBottom.itemcontentGiftimage.setOnClickListener {
            onGoldClicked(post)
        }
        binding.root.setOnClickListener {
            onContentClicked(post)
        }
    }
}