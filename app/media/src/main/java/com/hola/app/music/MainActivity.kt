package com.hola.app.music

import android.os.Bundle
import android.os.Environment
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.SimpleAdapter
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
    private val adapter = MediaItemAdapter(this)

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
        view.mediaList.adapter = adapter
    }


    fun initWithData() {
        val file = File(Environment.getExternalStorageDirectory(), "video.mp4")
        Logcat.d("Main", "file path--->${file.absolutePath}")
        model.input(MainViewAction.SubscribeMediaData("abcdefg"))
        model.output(this) {
            when (it) {
                is MainViewState.MediaData->{
                    Logcat.d("Main", "media data--->${it.mediaData}")
                    adapter.setItems(it.mediaData)
                }
            }
        }
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