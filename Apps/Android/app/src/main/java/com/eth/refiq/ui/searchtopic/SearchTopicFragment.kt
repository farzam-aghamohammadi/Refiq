package com.eth.refiq.ui.searchtopic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.eth.refiq.databinding.FragmentSearchTopicBinding
import com.eth.refiq.ui.searchtopic.adapter.TopicAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchTopicFragment : Fragment() {

    private var _binding: FragmentSearchTopicBinding? = null

    private val binding get() = _binding!!

    private val searchTopicViewModel: SearchTopicViewModel by viewModel()
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
        binding.searchtopicSearchview.onActionViewExpanded()
        /*   binding.searchviewSearchtopic.requestFocus()
           binding.searchviewSearchtopic.requestFocusFromTouch()*/

        val adapter = TopicAdapter { topic ->
        }
        binding.searchtopicList.adapter = adapter
        searchTopicViewModel.topicsLiveData.observe(viewLifecycleOwner) {
            adapter.updateAdapter(it)

        }




        observeSearchQuery()

    }
    private fun observeSearchQuery(){
        binding.searchtopicSearchview.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchTopicViewModel.onSearchTopicChanged(query)

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println("onQueryTextChange $newText")
                newText?.let {
                    searchTopicViewModel.onSearchTopicChanged(newText)

                }
                return true
            }

        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}