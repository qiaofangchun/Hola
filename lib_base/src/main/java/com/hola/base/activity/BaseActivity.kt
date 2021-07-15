package com.hola.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
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

    protected fun registerListener(){}

    protected fun unregisterListener(){}
}