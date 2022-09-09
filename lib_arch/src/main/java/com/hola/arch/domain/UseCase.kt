package com.hola.arch.domain

import com.hola.arch.exception.BaseException
import com.hola.arch.exception.DataNullException
import kotlinx.coroutines.*
import java.lang.Exception

abstract class UseCase(protected val coroutineScope: CoroutineScope) {

    fun <T> doRequest(request: (suspend () -> T?)): UseCaseExecutor<T> {
        return UseCaseExecutor(mScope, this::handException, request)
    }

    protected suspend fun <T> sync(block: suspend () -> T): T {
        return block.invoke()
    }

    protected suspend fun <T> async(block: suspend () -> T): Deferred<T> {
        val deferred = mScope.async(start = CoroutineStart.LAZY) {
            block.invoke()
        }
        deferred.start()
        return deferred
    }

    abstract fun handException(throwable: Throwable): BaseException

    class UseCaseExecutor<T>(
        private val mScope: CoroutineScope,
        private val mHandler: ((Throwable) -> BaseException),
        private val mRequest: (suspend () -> T?)? = null
    ) {
        private var mOnStart: (() -> Unit)? = null
        private var mOnSuccess: ((T) -> Unit)? = null
        private var mOnFailure: ((Exception) -> Unit)? = null

        fun onStart(onStart: (() -> Unit)?): UseCaseExecutor<T> {
            this.mOnStart = onStart
            return this
        }

        fun onSuccess(onSuccess: ((T) -> Unit)?): UseCaseExecutor<T> {
            this.mOnSuccess = onSuccess
            return this
        }

        fun onFailure(onFailure: ((Exception) -> Unit)?): UseCaseExecutor<T> {
            this.mOnFailure = onFailure
            return this
        }

        fun execute() {
            mOnStart?.invoke()
            val exceptionHandler = CoroutineExceptionHandler { _, exception ->
                mOnFailure?.invoke(mHandler.invoke(exception))
            }
            mScope.launch(exceptionHandler) {
                withContext(Dispatchers.IO) {
                    mRequest?.invoke()
                }?.let {
                    mOnSuccess?.invoke(it as T)
                } ?: let {
                    mOnFailure?.invoke(mHandler.invoke(DataNullException()))
                }
            }
        }
    }
}