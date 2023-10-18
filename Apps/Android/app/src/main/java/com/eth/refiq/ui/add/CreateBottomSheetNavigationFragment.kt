package com.eth.refiq.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.eth.refiq.R
import com.eth.refiq.databinding.FragementBottomsheetNewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateTopicPostBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragementBottomsheetNewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragementBottomsheetNewBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newtopicTextview.setOnClickListener {
            findNavController().navigate(R.id.navigation_crete_topic)
        }
    }
}