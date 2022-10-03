package com.hola.arch.ui

import androidx.lifecycle.*
import androidx.lifecycle.Lifecycle.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class MviViewModel<A : MviViewAction, S : MviViewState<*>> : ViewModel() {
    private val isCollect = false
    private val stateFlows by lazy { getNeedCollectFlow() }

    protected abstract fun getNeedCollectFlow(): List<StateFlowWarp<S>>

    fun input(action: A) {
        viewModelScope.launch { handleInput(action) }
    }

    fun output(lifecycleOwner: LifecycleOwner, observer: (S) -> Unit) {
        stateFlows.forEach { warp -> warp.flow.collect(lifecycleOwner, warp.state, observer) }
    }

    private fun <T> Flow<T>.collect(lifecycleOwner: LifecycleOwner, state: State, observer: (T) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(state) {
                this@collect.collect { observer.invoke(it) }
            }
        }
    }

    protected open suspend fun handleInput(action: A) {}
}