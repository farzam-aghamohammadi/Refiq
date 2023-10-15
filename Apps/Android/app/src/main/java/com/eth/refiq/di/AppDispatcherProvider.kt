package com.eth.refiq.di
import CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppDispatcherProvider : CoroutineDispatcherProvider {
    override fun mainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    override fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

    override fun defaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}