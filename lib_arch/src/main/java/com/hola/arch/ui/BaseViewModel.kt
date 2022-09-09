package com.hola.arch.ui

import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class BaseViewModel<E> : ViewModel() {
    companion object {
        private const val DEFAULT_QUEUE_LENGTH = 10
    }

    private var _sharedFlow: MutableSharedFlow<E> = MutableSharedFlow(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = initQueueMaxLength()
    )
    private val delayMap: MutableMap<Int, Boolean> = mutableMapOf()

    protected open fun initQueueMaxLength(): Int {
        return DEFAULT_QUEUE_LENGTH
    }

    fun output(@NonNull activity: AppCompatActivity, @NonNull observer: (E) -> Unit) {
        delayMap[System.identityHashCode(activity)] = true
        activity.lifecycleScope.launch {
            activity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delayMap.remove(System.identityHashCode(activity))
                _sharedFlow.collect { observer.invoke(it) }
            }
        }
    }

    fun output(@NonNull fragment: Fragment, @NonNull observer: (E) -> Unit) {
        delayMap[System.identityHashCode(fragment)] = true
        fragment.viewLifecycleOwner.lifecycleScope.launch {
            fragment.viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delayMap.remove(System.identityHashCode(fragment))
                _sharedFlow.collect { observer.invoke(it) }
            }
        }
    }

    protected suspend fun sendResult(event: E) {
        _sharedFlow.emit(event)
    }

    fun input(event: E) {
        viewModelScope.launch {
            if (needDelayForLifecycleState) delayForLifecycleState().collect { onHandle(event) }
            else onHandle(event)
        }
    }

    private val needDelayForLifecycleState
        get() = delayMap.isNotEmpty()

    protected open suspend fun onHandle(event: E) {}

    private fun delayForLifecycleState() = flow {
        delay(1)
        emit(true)
    }
}