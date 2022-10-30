package com.hola.app.music

import android.os.Environment
import com.hola.app.music.databinding.ActivityMainBinding.bind
import com.hola.base.activity.BaseActivity
import com.hola.viewbind.viewBinding
import java.io.File

class MainActivity : BaseActivity(R.layout.activity_main) {
    private val view by viewBinding(::bind)
    private val player by lazy { VideoPlayer(view.surfaceView) }

    override fun initWithView() {
        view.startPlay.setOnClickListener{
            val file = File(Environment.getExternalStorageState(),"video.mp4")
            player.start(file.absolutePath)
        }
    }

    override fun initWithData() {
        view.version.text = player.getVersion()
    }
}