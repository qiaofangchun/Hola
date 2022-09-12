package com.hola.arch.domain

import com.hola.arch.exception.BaseException
import kotlinx.coroutines.CoroutineScope

abstract class UseCase(protected val coroutineScope: CoroutineScope) {
    abstract fun handException(throwable: Throwable): BaseException
}