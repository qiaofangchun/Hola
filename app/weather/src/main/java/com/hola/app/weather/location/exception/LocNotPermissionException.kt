package com.hola.app.weather.location.exception

import com.hola.common.exception.BaseException

class LocNotPermissionException : BaseException {
    constructor() : this("No permissions")

    constructor(msg: String) : this(0,msg)

    constructor(code: Int, msg: String) : super(code,msg)
}