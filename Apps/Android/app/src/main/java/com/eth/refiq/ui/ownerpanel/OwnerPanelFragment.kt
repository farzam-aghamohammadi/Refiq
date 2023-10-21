package com.eth.refiq.ui.ownerpanel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eth.refiq.R
import com.eth.refiq.databinding.FragmentOwnerPanelBinding
import com.eth.refiq.databinding.FragmentWalletBinding
import com.eth.refiq.ui.ownerpanel.adapter.TopicOwnerPanelAdapter
import com.eth.refiq.ui.wallet.WalletViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class OwnerPanelFragment : Fragment() {
    private var _binding: FragmentOwnerPanelBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val walletViewModel: WalletViewModel by activityViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOwnerPanelBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topicownerpanelViewpager.adapter = TopicOwnerPanelAdapter(childFragmentManager)
        binding.topicownerpanelTablayout.setupWithViewPager(binding.topicownerpanelViewpager)
    }
}