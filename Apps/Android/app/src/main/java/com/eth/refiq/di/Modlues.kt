package com.eth.refiq.di

import com.eth.refiq.MainViewModel
import com.eth.refiq.ui.connectwallet.ConnectWalletViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel()
    }
    viewModel {
        ConnectWalletViewModel()
    }
}
