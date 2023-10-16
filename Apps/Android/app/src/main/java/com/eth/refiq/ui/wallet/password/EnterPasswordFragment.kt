package com.eth.refiq.ui.wallet.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.eth.refiq.R
import com.eth.refiq.databinding.FragmentEnterPasswordBinding
import com.eth.refiq.databinding.FragmentWalletBinding
import com.eth.refiq.ui.wallet.WalletViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class EnterPasswordFragment : DialogFragment() {
    private var _binding: FragmentEnterPasswordBinding? = null

    private val walletViewModel:WalletViewModel by activityViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.enterpassButton.setOnClickListener {
            onContinueClicked()
        }
    }

    private fun onContinueClicked() {
        val password = binding.enterpassEdittext.text.toString()
        when (arguments?.getString(DESTINATION)) {
            DESTINATION_IMPORT_WALLET -> {

            }

            DESTINATION_NEW_WALLET -> {
                walletViewModel.createWallet(password)
                findNavController().navigate(R.id.action_to_new_wallet, Bundle().apply {
                    putString(PASSWORD, password)
                })
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val PASSWORD = "password"
        const val DESTINATION = "destination"
        const val DESTINATION_IMPORT_WALLET = "import_wallet"
        const val DESTINATION_NEW_WALLET = "new_wallet"
    }
}