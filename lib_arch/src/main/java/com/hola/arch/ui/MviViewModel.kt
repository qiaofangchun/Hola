package com.hola.arch.ui

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class MviViewModel : ViewModel() {
    private val isCollect = false
    private lateinit var stateFlows: List<StateFlowWarp>

    protected abstract fun getNeedCollectFlow(): List<StateFlowWarp>

    fun input(action: MviViewAction) {
        viewModelScope.launch { handleInput(action) }
    }

    fun output(lifecycleOwner: LifecycleOwner, observer: (MviViewState<*>) -> Unit) {
        if (isCollect) return
        stateFlows = getNeedCollectFlow()
        stateFlows.forEach { warp -> warp.flow.collect(lifecycleOwner, warp.state, observer) }
    }

    private fun <T> Flow<T>.collect(lifecycleOwner: LifecycleOwner, state: Lifecycle.State, observer: (T) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(state) {
                this@collect.collect { observer.invoke(it) }
            }
        }
    }

    protected open suspend fun handleInput(action: MviViewAction) {}
}