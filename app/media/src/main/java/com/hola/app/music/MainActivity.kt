package com.hola.app.music

import android.os.Bundle
import android.os.Environment
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.PlaybackStateCompat
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
            //MediaControllerHelper.skipToNext()
            MediaControllerHelper.playFromSearch("abcde")

            MediaControllerHelper.subscribeMediaData(
                mediaId = "Because Of You",
                callback = callback
            )
        }
        view.prev.setOnClickListener {
            //MediaControllerHelper.skipToPrevious()
            MediaControllerHelper.playFromMediaId("1024")
        }
    }

    private val callback = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>,
            options: Bundle
        ) {
            Logcat.d("Main", "onChildrenLoaded--->$parentId")
        }

        override fun onError(parentId: String, options: Bundle) {
            Logcat.d("Main", "onError--->$parentId")
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
    }

    public override fun onResume() {
        super.onResume()
    }


    public override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        MediaControllerHelper.unsubscribeMediaData("Because Of You", callback)
        super.onDestroy()
    }
}