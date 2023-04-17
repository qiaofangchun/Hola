package com.hola.app.music

import android.os.Bundle
import android.os.Environment
import android.support.v4.media.session.PlaybackStateCompat
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.hola.app.music.databinding.ActivityMainBinding.bind
import com.hola.common.utils.Logcat
import com.hola.media.core.MediaControllerHelper
import com.hola.viewbind.viewBinding
import java.io.File

class MainActivity : FragmentActivity(R.layout.activity_main) {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val view by viewBinding(::bind)
    private val model by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWithView()
        initWithData()
    }

    fun initWithView() {
        view.version.setOnClickListener {
            if (MediaControllerHelper.playState == PlaybackStateCompat.STATE_PLAYING) {
                MediaControllerHelper.pause()
            } else {
                MediaControllerHelper.play()
            }
        }
        view.next.setOnClickListener {
            MediaControllerHelper.skipToNext()
        }
        view.prev.setOnClickListener {
            MediaControllerHelper.skipToPrevious()
        }
    }


    fun initWithData() {
        val file = File(Environment.getExternalStorageDirectory(), "video.mp4")
        Logcat.d("Main", "file path--->${file.absolutePath}")
        model.input(MainViewAction.SubscribeMediaData("abcdefg"))
        model.output(this) {
            when (it) {
                is MainViewState.MediaData->Logcat.d("Main", "load success")
            }
        }
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
    }

    public override fun onResume() {
        super.onResume()
    }


    public override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}