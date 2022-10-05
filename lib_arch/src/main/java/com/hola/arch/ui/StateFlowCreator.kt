package com.hola.arch.ui

import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.flow.MutableStateFlow

class StateFlowCreator<T : MviViewState<*>>(
    viewState: T,
    val lifecycleState: Lifecycle.State = Lifecycle.State.STARTED
) {
    val flow = MutableStateFlow(viewState)
    val stateClass = viewState.javaClass
}