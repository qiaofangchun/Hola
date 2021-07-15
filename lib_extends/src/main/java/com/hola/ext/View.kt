package com.hola.ext

import android.view.View
import androidx.annotation.IdRes

fun <T : View?> View.findViewById(@IdRes id: Int): T = this.findViewById(id)