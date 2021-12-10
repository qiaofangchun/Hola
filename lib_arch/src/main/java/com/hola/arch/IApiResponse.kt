package com.hola.arch

interface IApiResponse<T> {
    fun isSuccess(): Boolean

    fun getResult(): T

    fun getErrorCode(): Int

    fun getErrorMessage(): String
}