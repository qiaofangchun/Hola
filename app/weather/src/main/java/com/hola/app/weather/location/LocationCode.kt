package com.hola.app.weather.location

import androidx.annotation.IntDef

@IntDef(
    LocationCode.SUCCESS,
    LocationCode.FAILURE,
    LocationCode.NO_PERMISSION,
    LocationCode.NOT_FOUND_DEVICE
)
@Retention(AnnotationRetention.SOURCE)
annotation class LocationCode {
    companion object {
        const val FAILURE = -1
        const val SUCCESS = 0
        const val NO_PERMISSION = 1
        const val NOT_FOUND_DEVICE = 2
    }
}