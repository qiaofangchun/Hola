package com.hola.app.music

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import com.hola.app.music.databinding.ActivityMainBinding.bind
import com.hola.base.activity.BaseActivity
import com.hola.common.utils.Logcat
import com.hola.viewbind.viewBinding
import java.io.File

class MainActivity : BaseActivity(R.layout.activity_main) {
    private val view by viewBinding(::bind)
    private var isPause = false

    //private val player by lazy { VideoPlayer(view.surfaceView) }
    private val player by lazy { MusicPlayer() }
    val permission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.containsValue(false) || map.containsValue(null)) {
                return@registerForActivityResult
            }
            val file = File(Environment.getExternalStorageDirectory(), "music.mp3")
            Logcat.init("music")

            Logcat.d("Main", "file path--->${file.absolutePath}")
            player.setDataSource(file.absolutePath)
            player.prepare()
        }

    override fun initWithView() {
        view.startPlay.setOnClickListener {
            player.play()
        }
        view.pause.setOnClickListener {
            if (isPause) {
                isPause = false
                player.play()
            } else {
                isPause = true
                player.pause()
            }
        }
        view.seek.setOnClickListener {
            player.seekTo(0L)
        }
    }

    override fun initWithData() {
        //view.version.text = player.getVersion()
        permission.launch(arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE))
    }
}