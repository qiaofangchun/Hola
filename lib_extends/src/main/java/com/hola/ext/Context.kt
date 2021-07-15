package com.hola.ext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

inline val Context.screenWidth:Int
    get() = this.resources.displayMetrics.widthPixels

inline val Context.screenHeight:Int
    get() = this.resources.displayMetrics.heightPixels

fun Context.inflate(@LayoutRes resId: Int): View =
    this.inflate(resId, null, false)

fun Context.inflate(@LayoutRes resId: Int, root: ViewGroup?): View =
    this.inflate(resId, root, false)

fun Context.inflate(@LayoutRes resId: Int, root: ViewGroup?, attachToRoot: Boolean): View =
    LayoutInflater.from(this).inflate(resId, root, attachToRoot)