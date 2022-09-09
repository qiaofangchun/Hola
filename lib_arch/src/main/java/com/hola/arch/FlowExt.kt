package com.hola.arch

import kotlinx.coroutines.flow.flowOf

fun <T> T.asFlow() = flowOf(this)