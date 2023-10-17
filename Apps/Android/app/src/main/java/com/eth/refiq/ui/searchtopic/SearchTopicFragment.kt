package com.eth.refiq.ui.searchtopic
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eth.refiq.R
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
       //
        /*   binding.searchviewSearchtopic.requestFocus()
           binding.searchviewSearchtopic.requestFocusFromTouch()*/

       // binding.searchtopicSearchview.onActionViewExpanded()
        binding.searchtopicSearchview.onActionViewExpanded()
        //binding.searchtopicSearchview.requestFocus()
        showSoftKeyboard(binding.searchtopicSearchview)
        val adapter = TopicAdapter { topic ->
            findNavController().navigate(R.id.action_to_topic, Bundle().apply {
                putSerializable("topic", topic)
            })
        }
        binding.searchtopicList.adapter = adapter
        searchTopicViewModel.topicsLiveData.observe(viewLifecycleOwner) {
            adapter.updateAdapter(it)

        }




        observeSearchQuery()

    }
    fun showSoftKeyboard(view: View) {
        /*if (view.requestFocus()) {
            val imm = requireContext().getSystemService(InputMethodManager::class.java)
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }*/
        view.requestFocus()
        WindowCompat.getInsetsController(requireActivity().window, view)!!.show(WindowInsetsCompat.Type.ime())
    }
    private fun observeSearchQuery() {
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