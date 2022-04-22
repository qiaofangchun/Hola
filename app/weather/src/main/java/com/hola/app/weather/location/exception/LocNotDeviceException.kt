package com.hola.app.weather.location.exception

import com.hola.common.exception.BaseException

class LocNotDeviceException: BaseException {
    constructor() : this("Hardware device not found")

    constructor(msg: String) : this(0,msg)

    constructor(code: Int, msg: String) : super(code,msg)
}