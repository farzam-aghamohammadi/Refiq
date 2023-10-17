package com.eth.refiq.ui.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eth.refiq.R
import com.eth.refiq.databinding.FragmentWalletBinding
import com.eth.refiq.ui.wallet.password.EnterPasswordFragment
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

        observeIsWalletConnected()
        observeWalletInfo()
        binding.walletImport.setOnClickListener {
            findNavController().navigate(R.id.action_to_enter_password, Bundle().apply {
                putString(
                    EnterPasswordFragment.DESTINATION,
                    EnterPasswordFragment.DESTINATION_IMPORT_WALLET
                )
            })
        }
        binding.walletNew.setOnClickListener {
            findNavController().navigate(R.id.action_to_enter_password, Bundle().apply {
                putString(
                    EnterPasswordFragment.DESTINATION,
                    EnterPasswordFragment.DESTINATION_NEW_WALLET
                )
            })
        }

    }

    private fun observeWalletInfo() {
        walletViewModel.walletInfo.observe(viewLifecycleOwner) {
            binding.walletviewaccountTextviewBalance.text = it.balance
            binding.walletviewaccountTextviewAddress.text=it.address
        }
    }

    private fun observeIsWalletConnected() {
        walletViewModel.walletConnected.observe(viewLifecycleOwner) {
            if (it) {
                binding.walletViewAccount.isVisible = true
                binding.walletViewLogin.isGone = true
            } else {
                binding.walletViewAccount.isVisible = false
                binding.walletViewLogin.isGone = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}