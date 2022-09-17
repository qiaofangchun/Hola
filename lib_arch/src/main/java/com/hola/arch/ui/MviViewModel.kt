package com.hola.arch.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

open class MviViewModel : ViewModel() {
    private var version = START_VERSION
    private var currentVersion = START_VERSION
    private var observerCount = 0
    private val _sharedFlow: MutableSharedFlow<ConsumeOnceValue<MviViewState<*>>> by lazy {
        MutableSharedFlow(
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
            extraBufferCapacity = initQueueMaxLength(),
            replay = initQueueMaxLength()
        )
    }

    protected open fun initQueueMaxLength(): Int {
        return DEFAULT_QUEUE_LENGTH
    }

    fun output(activity: AppCompatActivity?, observer: (MviViewState<*>) -> Unit) {
        currentVersion = version
        observerCount++
        activity?.lifecycleScope?.launch {
            activity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                _sharedFlow.collect {
                    if (version > currentVersion) {
                        if (it.consumeCount >= observerCount) return@collect
                        it.consumeCount++
                        observer.invoke(it.value)
                    }
                }
            }
        }
    }

    fun output(fragment: Fragment?, observer: (MviViewState<*>) -> Unit) {
        currentVersion = version
        observerCount++
        fragment?.viewLifecycleOwner?.lifecycleScope?.launch {
            fragment.viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                _sharedFlow.collect {
                    if (version > currentVersion) {
                        if (it.consumeCount >= observerCount) return@collect
                        it.consumeCount++
                        observer.invoke(it.value)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        observerCount--
        super.onCleared()
    }

    protected suspend fun sendResult(event: MviViewState<*>) {
        version++
        _sharedFlow.emit(ConsumeOnceValue(value = event))
    }

    fun input(action: MviViewAction) {
        viewModelScope.launch { handleAction(action) }
    }

    protected open suspend fun handleAction(action: MviViewAction) {}

    data class ConsumeOnceValue<E>(
        var consumeCount: Int = 0,
        val value: E
    )

    companion object {
        private const val DEFAULT_QUEUE_LENGTH = 10
        private const val START_VERSION = -1
    }
}