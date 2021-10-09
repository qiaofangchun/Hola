package com.hola.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

abstract class BaseActivity(@LayoutRes private val resId: Int) : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resId)
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