package com.hola.arch.ui

import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.flow.Flow

class StateFlowWarp(
    val flow: Flow<MviViewState<*>>,
    val state: Lifecycle.State = Lifecycle.State.STARTED
)