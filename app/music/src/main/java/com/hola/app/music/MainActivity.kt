package com.hola.app.music

import com.hola.app.music.databinding.ActivityMainBinding.bind
import com.hola.base.activity.BaseActivity
import com.hola.viewbind.viewBinding

class MainActivity : BaseActivity(R.layout.activity_main) {
    private val view by viewBinding(::bind)

    override fun initWithView() {
    }

    override fun initWithData() {
        view.text.text = getFFmpegVersion()
    }

    private external fun getFFmpegVersion():String

    companion object {
        // Used to load the 'music' library on application startup.
        init {
            System.loadLibrary("music")
        }
    }
}