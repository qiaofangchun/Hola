package com.hola.arch.usecase

import com.hola.arch.IApiResponse
import com.hola.arch.exception.IException

interface UseCaseHandler {
    fun <T> handResponse(result: IApiResponse<T>): T?

    fun handException(throwable: Throwable): IException
}