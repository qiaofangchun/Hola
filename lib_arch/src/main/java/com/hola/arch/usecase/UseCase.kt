package com.hola.arch.usecase

import com.hola.arch.IApiResponse
import kotlinx.coroutines.CoroutineScope

open class UseCase(
    private val mScope: CoroutineScope,
    private val mHandler: UseCaseHandler
) {
    fun <T> doRequest(request: (suspend () -> T?)): UseCaseExecutor<T> {
        return UseCaseExecutor(mScope, mHandler, request)
    }

    fun <T> handResponse(result: IApiResponse<T>): T? {
        return mHandler.handResponse(result)
    }
}