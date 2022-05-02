package com.hola.app.weather.location

import com.hola.arch.exception.BaseException
import com.hola.location.annotation.LocationCode

class LocationException(@LocationCode errorCode: Int, message: String?) : BaseException(errorCode, message)