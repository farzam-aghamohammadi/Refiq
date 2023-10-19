package com.eth.refiq.ui.add.topic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.eth.refiq.R
import com.eth.refiq.databinding.FragmentCreateTopicBinding
import com.eth.refiq.ui.custom.showMessage
import com.eth.refiq.ui.topic.TopicViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateTopicFragment : Fragment() {
    private var _binding: FragmentCreateTopicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val topicViewModel: TopicViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateTopicBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.createtopicDone.onClicked = null
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createtopicDone.onClicked = {
            topicViewModel.createTopic(
                binding.createtopicName.text.toString(),
                binding.createtopicBio.text.toString(),
                binding.createtopicRules.text.toString()
            )
        }
        binding.createtopicDone.setText(getString(R.string.create))
        topicViewModel.creatingTopic.observe(viewLifecycleOwner) {
            if (it) {
                binding.createtopicDone.showLoading()
            } else {
                binding.createtopicDone.hideLoading()
            }
        }
        topicViewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                requireContext().showMessage(it)
            }
        }
    }


}