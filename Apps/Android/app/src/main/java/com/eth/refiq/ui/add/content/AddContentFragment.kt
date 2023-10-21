package com.eth.refiq.ui.add.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eth.refiq.databinding.FragmentAddContentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import com.eth.refiq.R
import com.eth.refiq.domain.ContentType
import com.eth.refiq.ui.custom.showMessage


class AddContentFragment : Fragment() {
    private var _binding: FragmentAddContentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val addContentViewModel: AddContentViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddContentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.addcontentCreateContentButton.onClicked = null
        _binding = null
    }

    private fun showSoftKeyboard(view: View) {
        view.requestFocus()
        WindowCompat.getInsetsController(requireActivity().window, view)
            .show(WindowInsetsCompat.Type.ime())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSoftKeyboard(binding.addcontentEditText)
        binding.addcontentPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            getImageResult.launch(intent)
        }
        binding.addcontentVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            getVideResult.launch(intent)
        }
        binding.addcontentEditText.addTextChangedListener {
            it.toString().let {
                addContentViewModel.onContentTextChanged(it)
            }
        }
        binding.addcontentCreateContentButton.setText(getString(R.string.create))
        binding.addcontentCreateContentButton.hideLoading()
        addContentViewModel.enableCreateContent.observe(viewLifecycleOwner) {
            binding.addcontentCreateContentButton.isGone = it.not()
        }
        val contentType = requireArguments().getSerializable(
            CONTENT_TYPE
        ) as ContentType

        binding.addcontentCreateContentButton.onClicked = {
            addContentViewModel.createContent(
                binding.addcontentEditText.text.toString(), contentType,
                requireArguments().getString(PARENT_ID, null)
            )
        }
        if (contentType == ContentType.POST) {
            binding.addcontentToolbar.title = getString(R.string.add_post_topic)
        } else {
            binding.addcontentToolbar.title = getString(R.string.add_comment)
        }

        addContentViewModel.creatingContent.observe(viewLifecycleOwner) { status ->
            when (status) {
                AddContentViewModel.CreatingContentStatus.Creating -> {
                    binding.addcontentCreateContentButton.showLoading()
                }
                AddContentViewModel.CreatingContentStatus.Created -> {
                    binding.addcontentCreateContentButton.hideLoading()
                    requireContext().showMessage("Created")
                    findNavController().popBackStack()
                }
                is AddContentViewModel.CreatingContentStatus.Failed -> {
                    binding.addcontentCreateContentButton.hideLoading()
                    (status).message?.let {
                        requireContext().showMessage(it)
                    }
                }
                else -> {}
            }
        }
    }


    private val getImageResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {

            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.data?.let {
                    addContentViewModel.onImageUriSelected(
                        getRealPathFromURI(
                            it,
                            requireContext()
                        )!!
                    )
                    binding.addcontentImageview.setImageURI(it)
                    binding.addcontentImageview.isVisible = true
                    binding.addcontentVideoview.isGone = true

                }
            }
        }


    private val getVideResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.data?.let {
                    addContentViewModel.onVideoUriSelected(
                        getRealPathFromURI(
                            it,
                            requireContext()
                        )!!
                    )
                    binding.addcontentVideoview.isVisible = true
                    binding.addcontentImageview.isGone = true
                    binding.addcontentVideoview.post {
                        binding.addcontentVideoview.setVideoURI(it)
                        binding.addcontentVideoview.start()
                    }
                }
            }
        }

    private fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val file = File(context.filesDir, name)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable: Int = inputStream?.available() ?: 0
            //int bufferSize = 1024;
            val bufferSize = Math.min(bytesAvailable, maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (inputStream?.read(buffers).also {
                    if (it != null) {
                        read = it
                    }
                } != -1) {
                outputStream.write(buffers, 0, read)
            }
            inputStream?.close()
            outputStream.close()

        } catch (e: java.lang.Exception) {
        }
        return file.path
    }

    companion object {
        const val PARENT_ID = "parent_id"
        const val CONTENT_TYPE = "content"
        const val COMMENT_CONTENT = "comment"
    }
}