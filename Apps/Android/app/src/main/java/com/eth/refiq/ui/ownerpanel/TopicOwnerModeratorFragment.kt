package com.eth.refiq.ui.ownerpanel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.eth.refiq.databinding.FragmentTopicownerModeratorsBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class TopicOwnerModeratorFragment : Fragment() {
    private var _binding: FragmentTopicownerModeratorsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: TopicOwnerViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTopicownerModeratorsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topicownerpanelAddmoderator.setOnClickListener {
            val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val editText = AppCompatEditText(requireContext())
            alert.setView(editText)
            alert.setPositiveButton(
                "Add Moderator"
            ) { dialog, which ->
                viewModel.addModerator(editText.text.toString())
            }
            alert.show()
        }
        viewModel.moderators.observe(viewLifecycleOwner) {
            binding.topicownerpanelModeratorlayout.removeAllViews()
            it.forEach {
                binding.topicownerpanelModeratorlayout.addView(
                    AppCompatTextView(requireContext()).apply {
                        text = it
                    }
                )
            }
        }
    }
}