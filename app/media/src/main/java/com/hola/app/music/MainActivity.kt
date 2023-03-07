package com.hola.app.music

import android.content.ComponentName
import android.os.Bundle
import android.os.Environment
import android.support.v4.media.MediaBrowserCompat
import androidx.fragment.app.FragmentActivity
import com.hola.app.music.databinding.ActivityMainBinding.bind
import com.hola.common.utils.Logcat
import com.hola.media.music.MusicService
import com.hola.viewbind.viewBinding
import java.io.File

class MainActivity : FragmentActivity(R.layout.activity_main) {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val view by viewBinding(::bind)

    private lateinit var mediaBrowser: MediaBrowserCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Logcat.init("music")
        initWithView()
        initWithData()
    }

    fun initWithView() {
        view.version.setOnClickListener {
            //mediaController.play()
        }
        view.next.setOnClickListener {
            //mediaController.next()
        }
        view.prev.setOnClickListener {
            //mediaController.prev()
        }
    }

    fun initWithData() {
        // Create MediaBrowserServiceCompat
        mediaBrowser = MediaBrowserCompat(
            this, ComponentName(this, MusicService::class.java),
            object : MediaBrowserCompat.ConnectionCallback() {
                override fun onConnected() {
                    Logcat.d(TAG, "[method=onConnected] MediaService connect success")
                }

                override fun onConnectionFailed() {
                    Logcat.d(TAG, "[method=onConnected] MediaService connect failed")
                }

                override fun onConnectionSuspended() {
                    Logcat.d(TAG, "[method=onConnected] MediaService connect suspended")
                }

            }, null
        )

        val file = File(Environment.getExternalStorageDirectory(), "video.mp4")
        Logcat.d("Main", "file path--->${file.absolutePath}")
        /*mediaProvider.addMedia(
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
        )*/
    }
}