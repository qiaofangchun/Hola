package com.hola.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity {

    constructor() : super()

    constructor(@LayoutRes resId: Int) : super(resId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWithView()
        initWithData()
    }

    protected abstract fun initWithView()

    protected abstract fun initWithData()

    override fun onDestroy() {
        super.onDestroy()
    }
}