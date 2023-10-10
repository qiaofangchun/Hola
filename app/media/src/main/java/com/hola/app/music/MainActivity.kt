package com.hola.app.music

import android.os.Bundle
import android.view.WindowManager
import com.hola.app.music.databinding.ActivityMainBinding
import com.hola.base.activity.BaseActivity
import com.hola.viewbind.viewBinding

class MainActivity : BaseActivity(R.layout.activity_main) {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val view by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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