package com.eth.refiq.ui.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eth.refiq.R
import com.eth.refiq.databinding.FragmentWalletBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class WalletFragment : Fragment() {

    private var _binding: FragmentWalletBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val walletViewModel: WalletViewModel by activityViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.walletClose.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.walletImport.setOnClickListener {
            findNavController().navigate(R.id.action_to_enter_password)
        }
        binding.walletNew.setOnClickListener {
            findNavController().navigate(R.id.action_to_enter_password)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}