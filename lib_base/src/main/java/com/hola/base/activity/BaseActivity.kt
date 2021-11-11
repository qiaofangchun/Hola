package com.hola.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(@LayoutRes resId: Int) : AppCompatActivity(resId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWithView()
        registerListener()
        initWithData()
    }

    override fun onDestroy() {
        unregisterListener()
        super.onDestroy()
    }

    protected abstract fun initWithView()

    protected abstract fun initWithData()

    protected fun registerListener() {}

    protected fun unregisterListener() {}
}