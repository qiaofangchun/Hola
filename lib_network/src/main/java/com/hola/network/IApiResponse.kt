package com.hola.network

interface IApiResponse<T> {
    fun isSuccess(): Boolean

    fun getResult(): T

    fun getResultCode(): Int

    fun getResultMsg(): String
}