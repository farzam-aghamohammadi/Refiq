package com.eth.refiq.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.eth.refiq.R

class AppProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var appCompatButton: AppCompatButton
    private var progressBar: ProgressBar
    var onClicked: (() -> Unit)? = null

    init {
        inflate(context, R.layout.view_progress_button, this)
        appCompatButton = findViewById(R.id.appprogressbutton_done)
        progressBar = findViewById(R.id.appprogressbutton_loading)

        appCompatButton.setOnClickListener {
            onClicked?.invoke()
        }

    }

    private var text: String? = null

    fun showLoading() {
        appCompatButton.isClickable = false
        appCompatButton.text = ""
        progressBar.isVisible = true
    }


    fun hideLoading() {
        appCompatButton.isClickable = true

        progressBar.isGone = true
        appCompatButton.text = text
    }

    fun setText(text: String) {
        this.text = text
        appCompatButton.text = text
    }
}