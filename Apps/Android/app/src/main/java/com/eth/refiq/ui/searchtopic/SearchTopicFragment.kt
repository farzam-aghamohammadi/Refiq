package com.eth.refiq.ui.searchtopic

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.eth.refiq.databinding.FragmentSearchTopicBinding


class SearchTopicFragment : Fragment() {

    private var _binding: FragmentSearchTopicBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchTopicBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchviewSearchtopic.onActionViewExpanded()
      /*  binding.searchviewSearchtopic.requestFocus()
        binding.searchviewSearchtopic.requestFocusFromTouch()*/


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}