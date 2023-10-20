package com.eth.refiq.ui.topic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eth.refiq.databinding.ItemPostImageBinding
import com.eth.refiq.databinding.ItemPostTextBinding
import com.eth.refiq.databinding.ItemPostVideoBinding
import com.eth.refiq.databinding.ItemTopicBinding
import com.eth.refiq.domain.Post
import com.eth.refiq.domain.PostType
import com.eth.refiq.domain.Topic
import com.eth.refiq.ui.searchtopic.adapter.TopicItemViewHolder

class PostAdapter constructor(private val onPostClicked: ((Post) -> Unit)) :
    RecyclerView.Adapter<PostItemViewHolder>() {
    private val items = mutableListOf<Post>()

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
    fun updateAdapter(items: List<Post>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].postType) {
            is PostType.Text -> TEXT_POST_TYPE
            is PostType.Image -> IMAGE_POST_TYPE
            is PostType.Video -> VIDEO_POST_TYPE
        }
    }

    companion object {
        private const val TEXT_POST_TYPE = 1
        private const val VIDEO_POST_TYPE = 2
        private const val IMAGE_POST_TYPE = 3
    }
}


abstract class PostItemViewHolder(private val view: ViewGroup) : RecyclerView.ViewHolder(view) {
    abstract fun bind(post: Post)
}

class PostTextItemViewHolder(private val binding: ItemPostTextBinding) :
    PostItemViewHolder(binding.root) {
    override fun bind(post: Post) {
        binding.itemcontentTextTop.itemcontentText.text = post.text
        binding.itemcontentTextTop.itemcontentAuthorid.text=post.walletAddress
        binding.itemcontentTextTop.itemcontentText.isGone=post.text.isEmpty()
    }
}

class PostVideoItemViewHolder(private val binding: ItemPostVideoBinding) :
    PostItemViewHolder(binding.root) {
    override fun bind(post: Post) {

    }
}

class PostImageItemViewHolder(private val binding: ItemPostImageBinding) :
    PostItemViewHolder(binding.root) {
    override fun bind(post: Post) {
        binding.itemImageTop.itemcontentAuthorid.text = post.walletAddress
        binding.itemImageTop.itemcontentText.text = post.text
        binding.itemImageTop.itemcontentText.isGone = post.text.isEmpty()
        Glide.with(binding.itemImageContent).load((post.postType as PostType.Image).uri)
            .into(binding.itemImageContent)
    }
}