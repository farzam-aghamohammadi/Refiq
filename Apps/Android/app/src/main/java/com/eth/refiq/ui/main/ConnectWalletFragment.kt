package com.eth.refiq.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.eth.refiq.Chains
import com.eth.refiq.R
import com.eth.refiq.databinding.FragmentConnectWalletBinding
import com.walletconnect.web3.modal.client.Web3Modal
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.walletconnect.web3.modal.ui.openWeb3Modal

class ConnectWalletFragment : Fragment() {

    private var _binding: FragmentConnectWalletBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val connectWalletViewModel:ConnectWalletViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentConnectWalletBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val chain= Chains.ETHEREUM_MAIN
        binding.buttonConnectWalletEth.setOnClickListener {

          /*  WalletConnectModal.setSessionParams(Modal.Params.SessionParams(
                requiredNamespaces = getRequiredNameSpace(),
            ))*/

            val ethereumChain = com.walletconnect.web3.modal.client.Modal.Model.Chain(
                chainName = "Ethereum",
                chainNamespace = "eip155",
                chainReference = "1",
                methods = Chains.Info.Eth.defaultMethods,
                events = Chains.Info.Eth.defaultEvents
            )

            val chains = listOf(ethereumChain)

            Web3Modal.setChains(chains)



            findNavController().openWeb3Modal(id = R.id.action_to_bottomSheet)


            //connectWalletViewModel.connectWithEthWallet()
        }

        return root
    }

/*    private fun getRequiredNameSpace(): Map<String, Modal.Model.Namespace.Proposal>{
        val ethChain= Chains.ETHEREUM_MAIN
        return mapOf(Pair(Chains.Info.Eth.chain,Modal.Model.Namespace.Proposal(
          chains =   listOf(ethChain.chainId),
            methods = Chains.Info.Eth.defaultMethods,
           events = Chains.Info.Eth.defaultEvents
        )))
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}