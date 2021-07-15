package com.hola.demo

import android.os.Bundle
import com.hola.base.activity.BaseActivity
import com.hola.demo.ui.main.MainFragment

class MainActivity(override val layoutResId: Int = R.layout.main_activity) : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment())
                    .commitNow()
        }
    }

    override fun initWithView() {
        TODO("Not yet implemented")
    }

    override fun initWithData() {
        TODO("Not yet implemented")
    }
}