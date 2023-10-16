package com.eth.refiq.ui.wallet.newwallet

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eth.refiq.R
import com.eth.refiq.databinding.FragmentNewWalletBinding
import com.eth.refiq.ui.wallet.WalletViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class NewWalletFragment : Fragment() {
    private var _binding: FragmentNewWalletBinding? = null

    private val walletViewModel: WalletViewModel by activityViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewWalletBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val binding get() = _binding!!
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        walletViewModel.mnemonicLiveData.observe(viewLifecycleOwner){
            binding.newwalletTextviewMnemonic.text=it
        }
        binding.newwalletCopy.setOnClickListener {
            val clipboard: ClipboardManager? =
                requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("label", binding.newwalletTextviewMnemonic.text.toString())
            clipboard?.setPrimaryClip(clip)

        }
        binding.newwalletDon.setOnClickListener {
            walletViewModel.saveMnemonic( binding.newwalletTextviewMnemonic.text.toString())
            findNavController().popBackStack(R.id.navigation_wallet,true)
        }
    }
}