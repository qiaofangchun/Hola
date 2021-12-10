package com.hola.arch.usecase

import com.hola.arch.exception.IException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UseCaseExecutor<T>(
    private val mScope: CoroutineScope,
    private val mHandler: UseCaseHandler,
    private val mRequest: (suspend () -> T?)? = null
) {
    private var mOnStart: (() -> Unit)? = null
    private var mOnSuccess: ((T) -> Unit)? = null
    private var mOnFailure: ((IException) -> Unit)? = null

    fun onStart(onStart: (() -> Unit)?): UseCaseExecutor<T> {
        this.mOnStart = onStart
        return this
    }

    fun onSuccess(onSuccess: ((T) -> Unit)?): UseCaseExecutor<T> {
        this.mOnSuccess = onSuccess
        return this
    }

    fun onFailure(onFailure: ((IException) -> Unit)?): UseCaseExecutor<T> {
        this.mOnFailure = onFailure
        return this
    }

    fun execute() {
        mOnStart?.invoke()
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            mOnFailure?.invoke(mHandler.handException(exception))
        }
        mScope.launch(exceptionHandler) {
            val result = mRequest?.invoke()
            result?.let {
                mOnSuccess?.invoke(it as T)
            } ?: mOnFailure?.invoke(mHandler.handException(NullPointerException("Request result is null")))
        }
    }
}
