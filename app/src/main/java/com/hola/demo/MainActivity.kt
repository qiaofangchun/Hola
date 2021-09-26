package com.hola.demo

import android.os.Bundle
import com.hola.base.activity.BaseActivity
import com.hola.demo.ui.main.MainFragment

class MainActivity : BaseActivity(R.layout.main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment())
                    .commitNow()
        }
    }

    override fun initWithView() {

    }

    override fun initWithData() {

    }
}