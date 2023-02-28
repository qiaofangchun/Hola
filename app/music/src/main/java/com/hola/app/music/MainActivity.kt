package com.hola.app.music

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.FragmentActivity
import com.hola.app.music.core.MediaController
import com.hola.app.music.core.MediaMetaData
import com.hola.app.music.core.MediaProvider
import com.hola.app.music.databinding.ActivityMainBinding.bind
import com.hola.common.utils.Logcat
import com.hola.viewbind.viewBinding
import java.io.File

class MainActivity : FragmentActivity(R.layout.activity_main) {
    private val view by viewBinding(::bind)
    private val mediaProvider = MediaProvider()
    private val mediaController = MediaController(this, mediaProvider)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logcat.init("music")
        initWithView()
        initWithData()
    }

    fun initWithView() {
        view.version.setOnClickListener {
            mediaController.play()
        }
        view.next.setOnClickListener {
            mediaController.next()
        }
        view.prev.setOnClickListener {
            mediaController.prev()
        }
    }

    fun initWithData() {
        val file = File(Environment.getExternalStorageDirectory(), "video.mp4")
        Logcat.d("Main", "file path--->${file.absolutePath}")
        mediaProvider.addMedia(
            MediaMetaData(
                url = "https://www.xzmp3.com/down/548fc0ca7dbb.mp3",
                name = "Because Of You"
            )
        )
        mediaProvider.addMedia(
            MediaMetaData(
                url = "https://www.xzmp3.com/down/8ca0713d630b.mp3",
                name = "错位时空"
            )
        )
        mediaProvider.addMedia(
            MediaMetaData(
                url = "https://www.xzmp3.com/down/fb8f3a845578.mp3",
                name = "星辰大海"
            )
        )
        mediaProvider.addMedia(
            MediaMetaData(
                url = "https://www.xzmp3.com/down/597caee79849.mp3",
                name = "See You Again"
            )
        )
    }
}