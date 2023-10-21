package com.eth.refiq.ui.contentdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import com.eth.refiq.databinding.FragmentContentDetailBinding
import com.eth.refiq.R
import com.eth.refiq.ui.contentdetail.adapter.ContentAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ContentDetailFragment : Fragment() {

    private var _binding: FragmentContentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContentDetailViewModel by viewModel {
        parametersOf(
            requireArguments().getStringArrayList(Moderators),
            requireArguments().getSerializable(ContentDetail) as ContentDetailInfo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contentInfoDetail =
            requireArguments().getSerializable(ContentDetail) as ContentDetailInfo
        if (contentInfoDetail is ContentDetailInfo.PostDetail) {
            binding.contentDetailToolbar.title = getString(R.string.post)
        } else if (contentInfoDetail is ContentDetailInfo.CommentDetail) {
            binding.contentDetailToolbar.title = getString(R.string.comment)

        }
        val adapter = ContentAdapter({}, {}, {})
        binding.contentDetailList.adapter = adapter
        viewModel.contentsLiveData.observe(viewLifecycleOwner) {
            println("update")
            binding.contentdetailCommentstextview.isGone = it.none()
            adapter.updateAdapter(it)
        }

        val mainContentAdapter = ContentAdapter({}, {}, {})
        binding.contentDetailMain.adapter = mainContentAdapter
        viewModel.mainContentLiveData.observe(viewLifecycleOwner) {
            mainContentAdapter.updateAdapter(listOf(it))
        }
        viewModel.canDelete.observe(viewLifecycleOwner) {
            binding.contentdetailCandeletelayout.isGone = it.not()
        }
    }

    companion object {
        const val ContentDetail = "content"
        const val Moderators = "moderator"
    }
}

