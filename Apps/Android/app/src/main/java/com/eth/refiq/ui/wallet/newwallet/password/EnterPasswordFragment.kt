package com.eth.refiq.ui.wallet.newwallet.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.eth.refiq.R
import com.eth.refiq.databinding.FragmentEnterPasswordBinding
import com.eth.refiq.databinding.FragmentWalletBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EnterPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EnterPasswordFragment : DialogFragment() {
    private var _binding: FragmentEnterPasswordBinding? = null

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

        }
    }
    private fun onContinueClicked(){
        val password=binding.enterpassEdittext.text.toString()
        when(arguments?.getString("destination")){
            "import"->{}
            "new"->{}

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}