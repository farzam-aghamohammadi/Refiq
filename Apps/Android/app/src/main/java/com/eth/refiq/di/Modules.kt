package com.eth.refiq.di

import CoroutineDispatcherProvider
import com.eth.refiq.MainViewModel
import com.eth.refiq.data.RemotePostRepository
import com.eth.refiq.data.RemoteTopicRepository
import com.eth.refiq.data.SharedPrefLocalDataStorage
import com.eth.refiq.domain.LocalDataStorage
import com.eth.refiq.domain.PostRepository
import com.eth.refiq.domain.TopicRepository
import com.eth.refiq.ui.connectwallet.ConnectWalletViewModel
import com.eth.refiq.ui.searchtopic.SearchTopicViewModel
import com.eth.refiq.ui.topic.TopicViewModel
import com.eth.refiq.ui.wallet.WalletViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel()
    }
    viewModel {
        ConnectWalletViewModel()
    }
    viewModel {
        TopicViewModel(get(),get(),get())
    }
    viewModel { SearchTopicViewModel(get(),get()) }

    viewModel{WalletViewModel()}

    single <TopicRepository>{ RemoteTopicRepository() }
    single <PostRepository>{ RemotePostRepository() }

    single<CoroutineDispatcherProvider> {
        AppDispatcherProvider()
    }
    single <LocalDataStorage> { SharedPrefLocalDataStorage(androidApplication())  }

}
