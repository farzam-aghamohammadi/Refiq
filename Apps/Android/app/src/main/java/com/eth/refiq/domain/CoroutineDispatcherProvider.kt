import kotlinx.coroutines.CoroutineDispatcher


interface CoroutineDispatcherProvider {
    fun mainDispatcher(): CoroutineDispatcher
    fun ioDispatcher(): CoroutineDispatcher
    fun defaultDispatcher(): CoroutineDispatcher
}