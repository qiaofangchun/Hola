package com.hola.location

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class IntDef(
    vararg val value: Int = [],
    val flag: Boolean = false,
    val open: Boolean = false
)