package com.eth.refiq.ui.topic

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eth.refiq.R
import com.eth.refiq.databinding.FragmentTopicBinding
import com.eth.refiq.domain.Content
import com.eth.refiq.domain.ContentType
import com.eth.refiq.domain.Topic
import com.eth.refiq.ui.add.content.AddContentFragment
import com.eth.refiq.ui.contentdetail.ContentDetailFragment
import com.eth.refiq.ui.contentdetail.ContentDetailInfo
import com.eth.refiq.ui.topic.adapter.PostAdapter
import com.eth.refiq.ui.topic.adapter.PostVideoItemViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class TopicFragment : Fragment() {
    private var _binding: FragmentTopicBinding? = null
    private var player: ExoPlayer? = null

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build()

    }

    private val binding get() = _binding!!

    private val topicViewModel: TopicViewModel by viewModel {
        parametersOf(requireArguments().getSerializable(TOPIC))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topic: Topic = arguments?.getSerializable(TOPIC) as Topic

        binding.topicToolbar.title = topic.name
        binding.topicTextviewTopicinfo.paintFlags =
            binding.topicTextviewTopicinfo.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.topicNewpost.setOnClickListener {
            findNavController().navigate(R.id.action_to_add_content, Bundle().apply {
                putSerializable(AddContentFragment.CONTENT_TYPE, ContentType.POST)
                putString(AddContentFragment.PARENT_ID, topic.id)
            })
        }
        binding.topicfragmentSwiperefreshlayout.setOnRefreshListener {
            topicViewModel.refresh()
        }
        val adapter = PostAdapter({

            findNavController().navigate(R.id.action_to_contentdetail, Bundle().apply {
                putStringArrayList(ContentDetailFragment.Moderators, ArrayList())
                putSerializable(
                    ContentDetailFragment.ContentDetail, ContentDetailInfo.PostDetail(
                        it.id,
                        Content(it.id, it.walletAddress, it.postType, it.text)
                    )
                )
            })
            println("clicked")
        }, { onCommentClicked ->
            findNavController().navigate(R.id.action_to_add_content, Bundle().apply {
                putSerializable(AddContentFragment.CONTENT_TYPE, ContentType.COMMENT)
                putString(AddContentFragment.PARENT_ID, onCommentClicked.id)
            })

        }, {

        })
        binding.topicListPost.adapter = adapter
        binding.topicListPost.addOnScrollListener(recyclerViewScrollChangeListener)
        binding.topicListPost.addRecyclerListener {
            println("hjbjklhbjlb")
        }
        topicViewModel.postsLiveData.observe(viewLifecycleOwner) {
            binding.topicfragmentSwiperefreshlayout.isRefreshing = false
            adapter.updateAdapter(it)

        }
        topicViewModel.isOwner.observe(viewLifecycleOwner) {
            binding.topicOwnerpanel.isVisible = it
        }
        binding.topicOwnerpanel.setOnClickListener {
            findNavController().navigate(R.id.action_to_owner_panel,Bundle().apply {
                putSerializable("topic",requireArguments().getSerializable(TOPIC))
            })
        }
    }

    private val recyclerViewScrollChangeListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                val visibleItemPosition =
                    (binding.topicListPost.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                if (recyclerView.findViewHolderForLayoutPosition(visibleItemPosition) is PostVideoItemViewHolder) {
                    (recyclerView.findViewHolderForLayoutPosition(visibleItemPosition) as? PostVideoItemViewHolder)?.showVideo(
                        exoPlayer = player!!
                    )?.apply {
                        if (currentPlayerItemPosition == -1) {
                            currentPlayerItemPosition = visibleItemPosition
                        }else {
                            (recyclerView.findViewHolderForLayoutPosition(currentPlayerItemPosition) as? PostVideoItemViewHolder)?.stopVideo()
                            currentPlayerItemPosition=visibleItemPosition
                        }
                    }
                } else {
                    player?.pause()
                }
            }
        }
    }
    private var currentPlayerItemPosition = -1;

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            exoPlayer.release()
        }
        player = null
    }

    override fun onResume() {
        super.onResume()
        if (player == null) {
            initializePlayer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.topicListPost.removeOnScrollListener(recyclerViewScrollChangeListener)
        _binding = null
    }

    companion object {
        const val TOPIC = "topic"
    }
}