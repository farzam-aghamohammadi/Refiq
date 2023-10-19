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


class TopicFragment : Fragment() {
    private var _binding: FragmentTopicBinding? = null

    private val binding get() = _binding!!

    private val topicViewModel: TopicViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topic: Topic = arguments?.getSerializable("topic") as Topic

        binding.topicToolbar.title = topic.name
        binding.topicTextviewTopicinfo.paintFlags = binding.topicTextviewTopicinfo.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.topicNewpost.setOnClickListener {
            findNavController().navigate(R.id.action_to_add_content)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}