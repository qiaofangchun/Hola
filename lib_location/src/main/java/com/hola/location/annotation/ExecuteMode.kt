package com.hola.location.annotation

import androidx.annotation.IntDef

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