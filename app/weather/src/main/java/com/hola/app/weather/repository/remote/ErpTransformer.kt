package com.hola.app.weather.repository.remote

import com.hola.app.weather.repository.remote.model.ApiResult
import com.hola.arch.exception.NetApiException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.transform

fun <T> Flow<T>.handleNetApiResult(): Flow<T> = this.transform { result ->
    if (result !is ApiResult) {
        throw NetApiException(message = "Service Api response not support")
    }
    when (result.status) {
        STATUS_SUCCESS -> emit(result)
        STATUS_FAILURE -> {
            val msg = "Service Api status:${result.status}, msg:${result.error}"
            throw NetApiException(message = msg)
        }
        else -> throw NetApiException(message = result.status)
    }
}.catch { ex ->
    if (ex is NetApiException) {
        throw ex
    } else {
        throw NetApiException(message = ex.message, cause = ex)
    }
}