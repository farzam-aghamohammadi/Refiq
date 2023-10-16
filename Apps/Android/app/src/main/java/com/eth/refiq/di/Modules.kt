package com.eth.refiq.di

import CoroutineDispatcherProvider
import com.eth.refiq.MainViewModel
import com.eth.refiq.data.RemotePostRepository
import com.eth.refiq.data.RemoteTopicRepository
import com.eth.refiq.data.SharedPrefLocalDataStorage
import com.eth.refiq.data.Web3JRepository
import com.eth.refiq.data.Web3JWalletRepository
import com.eth.refiq.domain.LocalDataStorage
import com.eth.refiq.domain.PostRepository
import com.eth.refiq.domain.TopicRepository
import com.eth.refiq.domain.WalletRepository
import com.eth.refiq.domain.Web3Repository
import com.eth.refiq.ui.connectwallet.ConnectWalletViewModel
import com.eth.refiq.ui.searchtopic.SearchTopicViewModel
import com.eth.refiq.ui.topic.TopicViewModel
import com.eth.refiq.ui.wallet.WalletViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<TopicRepository> { RemoteTopicRepository() }
    single<PostRepository> { RemotePostRepository() }
    single<WalletRepository> { Web3JWalletRepository(get()) }
    single<CoroutineDispatcherProvider> {
        AppDispatcherProvider()
    }
    single<LocalDataStorage> { SharedPrefLocalDataStorage(androidApplication()) }

    single<Web3Repository> { Web3JRepository(androidApplication()) }


    viewModel {
        MainViewModel()
    }
    viewModel {
        ConnectWalletViewModel()
    }
    viewModel {
        TopicViewModel(get(), get(), get())
    }
    viewModel { SearchTopicViewModel(get(), get()) }

    viewModel { WalletViewModel(get(), get(),get()) }

}
