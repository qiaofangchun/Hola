package com.hola.location.annotation

import com.hola.location.IntDef

@IntDef(
    ExecuteMode.SEPARATE,
    ExecuteMode.CONCURRENT,
)
@Retention(AnnotationRetention.SOURCE)
annotation class ExecuteMode {
    companion object {
        const val SEPARATE = 0
        const val CONCURRENT = 1
    }
}