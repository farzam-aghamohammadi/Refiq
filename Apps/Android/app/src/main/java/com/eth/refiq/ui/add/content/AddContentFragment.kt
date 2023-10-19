package com.eth.refiq.ui.add.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import com.eth.refiq.databinding.FragmentAddContentBinding
import com.eth.refiq.ui.topic.TopicViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


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
        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val size = returnCursor.getLong(sizeIndex).toString()
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

}