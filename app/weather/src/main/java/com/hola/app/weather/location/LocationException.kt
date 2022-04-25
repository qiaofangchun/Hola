package com.hola.app.weather.location

import com.hola.arch.exception.BaseException

class LocationException(@LocationCode errorCode: Int, message: String?) : BaseException(errorCode, message)