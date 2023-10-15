package com.eth.refiq.di

import CoroutineDispatcherProvider
import com.eth.refiq.MainViewModel
import com.eth.refiq.data.RemoteTopicRepository
import com.eth.refiq.domain.TopicRepository
import com.eth.refiq.ui.connectwallet.ConnectWalletViewModel
import com.eth.refiq.ui.searchtopic.SearchTopicViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel()
    }
    viewModel {
        ConnectWalletViewModel()
    }
    viewModel { SearchTopicViewModel(get(),get()) }
    single <TopicRepository>{ RemoteTopicRepository() }
    single<CoroutineDispatcherProvider> {
        AppDispatcherProvider()
    }

}
