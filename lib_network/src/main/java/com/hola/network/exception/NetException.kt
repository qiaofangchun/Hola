package com.hola.network.exception

open class NetException(val errorCode: Int, message: String?, cause: Throwable?) :
    Exception(message, cause) {
    companion object {
        private const val CODE = Int.MIN_VALUE
    }

    constructor() : this(CODE, null, null)

    constructor(msg: String?) : this(CODE, msg)

    constructor(code: Int, msg: String?) : this(code, msg, null)

    constructor(cause: Throwable?) : this(CODE, cause)

    constructor(code: Int, cause: Throwable?) : this(code, cause?.toString(), cause)
}