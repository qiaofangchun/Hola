package com.hola.arch.exception

interface IException {
    fun getErrorCode(): Int

    fun getErrorMessage(): String
}