package com.hola.arch.ui

import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.flow.Flow

class StateFlowWarp<T : MviViewState<*>>(
    val flow: Flow<T>,
    val state: Lifecycle.State = Lifecycle.State.STARTED
)