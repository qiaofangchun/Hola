package com.hola.arch.core

import androidx.lifecycle.*
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.set

abstract class ViewModel<I : ViewIntent, S : ViewState> : ViewModel() {
    private val stateFlowMap = mutableMapOf<Class<S>, StateFlowCreator<S>>()

    init {
        this.initUiState()
    }

    protected abstract fun initUiState()

    protected open fun addUiState(uiState: S, state: State = State.STARTED) {
        stateFlowMap[uiState.javaClass] = StateFlowCreator(uiState, state)
    }

    fun input(intent: I) {
        viewModelScope.launch { onInput(intent) }
    }

    fun output(lifecycleOwner: LifecycleOwner, observer: (S) -> Unit) {
        stateFlowMap.forEach {
            it.value.flow.collect(
                lifecycleOwner, it.value.lifecycleState, observer
            )
        }
    }

    private fun <S> Flow<S>.collect(
        lifecycleOwner: LifecycleOwner, state: State, observer: (S) -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(state) {
                this@collect.collect { observer.invoke(it) }
            }
        }
    }

    protected suspend fun output(uiState: S) {
        val clzName = uiState.javaClass
        stateFlowMap[clzName]?.flow?.emit(uiState)
            ?: throw IllegalArgumentException("Not fund UiState($clzName), Please add it first in initUiState.")
    }

    protected open suspend fun onInput(intent: I) {}

    private class StateFlowCreator<T : ViewState>(uiState: T, val lifecycleState: State) {
        val flow = MutableStateFlow(uiState)
    }
}