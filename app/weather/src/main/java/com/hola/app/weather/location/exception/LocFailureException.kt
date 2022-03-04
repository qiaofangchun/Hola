package com.hola.app.weather.location.exception

import com.hola.arch.exception.BaseException

class LocFailureException : BaseException {
    constructor() : this("")

    constructor(msg: String) : super(msg)
}