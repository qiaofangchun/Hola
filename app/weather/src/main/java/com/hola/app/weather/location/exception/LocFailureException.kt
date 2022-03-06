package com.hola.app.weather.location.exception

import com.hola.arch.exception.BaseException

class LocFailureException : BaseException {
    constructor() : this("Location failed")

    constructor(msg: String) : this(0,msg)

    constructor(code: Int, msg: String) : super(code,msg)
}