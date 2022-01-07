package com.hola.arch.usecase

import com.hola.arch.exception.ApiException

interface UseCaseHandler {
    fun <T> handResponse(result: Result<T>): T?

    fun handException(throwable: Throwable): ApiException
}