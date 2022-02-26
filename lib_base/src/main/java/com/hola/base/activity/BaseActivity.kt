package com.hola.base.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity {

    constructor() : super()

    constructor(@LayoutRes resId: Int) : super(resId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWithView()
        registerListener()
        initWithData()
    }

    protected open fun requestedOrientation(): Int = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    protected abstract fun initWithView()

    protected abstract fun initWithData()

    protected fun registerListener() {}

    protected fun unregisterListener() {}

    override fun onDestroy() {
        unregisterListener()
        super.onDestroy()
    }
}