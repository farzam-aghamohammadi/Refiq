package com.eth.refiq.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _loggedIn = MutableLiveData<Boolean>().apply {
        value = false
    }
    val loggedIn: LiveData<Boolean> = _loggedIn
}