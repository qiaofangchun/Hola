package com.hola.app.music

import android.media.AudioManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.media.session.PlaybackStateCompat
import androidx.fragment.app.FragmentActivity
import com.hola.app.music.databinding.ActivityMainBinding.bind
import com.hola.common.utils.Logcat
import com.hola.media.core.MediaBrowserHelper
import com.hola.viewbind.viewBinding
import java.io.File

class MainActivity : FragmentActivity(R.layout.activity_main) {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val view by viewBinding(::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWithView()
        initWithData()
    }

    fun initWithView() {
        view.version.setOnClickListener {
            //mediaController.play()
            MediaBrowserHelper.mediaController?.let {
                val pbState = it.playbackState?.state
                if (pbState == PlaybackStateCompat.STATE_PLAYING) {
                    it.transportControls.pause()
                } else {
                    it.transportControls.play()
                }
            }
        }
        view.next.setOnClickListener {
            //mediaController.next()
            MediaBrowserHelper.mediaController?.transportControls?.skipToNext()
        }
        view.prev.setOnClickListener {
            //mediaController.prev()
            MediaBrowserHelper.mediaController?.transportControls?.skipToPrevious()
        }
    }

    fun initWithData() {
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

    public override fun onStart() {
        super.onStart()
        MediaBrowserHelper.connect()
    }

    public override fun onResume() {
        super.onResume()
        volumeControlStream = AudioManager.STREAM_MUSIC
    }


    public override fun onStop() {
        super.onStop()
        MediaBrowserHelper.disconnect()
    }
}