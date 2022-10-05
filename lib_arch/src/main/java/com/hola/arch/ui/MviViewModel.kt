package com.hola.arch.ui

import androidx.lifecycle.*
import androidx.lifecycle.Lifecycle.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class MviViewModel<A : MviViewAction, S : MviViewState<*>> : ViewModel() {
    private val stateFlowMap by lazy {
        val map = mutableMapOf<Class<S>, StateFlowCreator<S>>()
        configStateFlow().forEach{ map[it.stateClass] = it }
        return@lazy map
    }

    protected abstract fun configStateFlow(): List<StateFlowCreator<S>>

    fun input(action: A) {
        viewModelScope.launch { handleInput(action) }
    }

    fun output(lifecycleOwner: LifecycleOwner, observer: (S) -> Unit) {
        stateFlowMap.forEach { it.value.flow.collect(lifecycleOwner, it.value.lifecycleState, observer) }
    }

    private fun <T> Flow<T>.collect(lifecycleOwner: LifecycleOwner, state: State, observer: (T) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(state) {
                this@collect.collect { observer.invoke(it) }
            }
        }
    }

    protected suspend fun output(state: S) {
        stateFlowMap[state::class.java]?.flow?.emit(state)
    }

    protected open suspend fun handleInput(action: A) {}
}