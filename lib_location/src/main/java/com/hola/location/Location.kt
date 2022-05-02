package com.hola.location

import com.hola.location.annotation.LocationCode
import java.lang.IllegalArgumentException

data class Location(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val province: String? = null,
    val city: String? = null,
    val district: String? = null,
    val street: String? = null,
    val address: String? = null,
    @LocationCode val errorCode: Int = LocationCode.FAILURE,
    val message: String? = null
) {
    constructor(@LocationCode code: Int, message: String?) : this(
        errorCode = code,
        message = message
    ){
        if (code== LocationCode.SUCCESS){
            throw IllegalArgumentException("This errorCode cannot be 0")
        }
    }
}