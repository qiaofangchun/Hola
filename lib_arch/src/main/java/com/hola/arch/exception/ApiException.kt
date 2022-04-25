package com.hola.arch.exception

class ApiException(code: Int, cause: Throwable?) : BaseException(code, cause)