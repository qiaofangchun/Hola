package com.hola.location.annotation

import com.hola.location.IntDef

@IntDef(
    LocationMode.MODE_GPS,
    LocationMode.MODE_NETWORK,
    LocationMode.MODE_OTHER
)
@Retention(AnnotationRetention.SOURCE)
annotation class LocationMode {
    companion object {
        const val MODE_GPS = 0
        const val MODE_NETWORK = 1
        const val MODE_OTHER = -1
    }
}