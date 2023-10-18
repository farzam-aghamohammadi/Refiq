package com.eth.refiq.ui.add.topic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eth.refiq.R
import com.eth.refiq.databinding.FragementBottomsheetNewBinding
import com.eth.refiq.databinding.FragmentCreateTopicBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CreateTopicFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCreateTopicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        _binding = null
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }



}