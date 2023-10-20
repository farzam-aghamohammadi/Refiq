package com.eth.refiq.ui.topic

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eth.refiq.databinding.FragmentTopicBinding
import com.eth.refiq.domain.Topic
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.eth.refiq.R
import com.eth.refiq.domain.ContentType
import com.eth.refiq.ui.add.content.AddContentFragment
import com.eth.refiq.ui.add.content.AddContentViewModel
import com.eth.refiq.ui.topic.adapter.PostAdapter
import org.koin.core.parameter.parametersOf


class TopicFragment : Fragment() {
    private var _binding: FragmentTopicBinding? = null

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
        val adapter = PostAdapter({

        })
        binding.topicListPost.adapter = adapter
        topicViewModel.postsLiveData.observe(viewLifecycleOwner) {
            adapter.updateAdapter(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TOPIC = "topic"
    }
}