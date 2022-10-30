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
    private val player by lazy { VideoPlayer(view.surfaceView) }
    val permission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.containsValue(false) || map.containsValue(null)) {
                return@registerForActivityResult
            }
            val file = File(Environment.getExternalStorageDirectory(), "video.mp4")
            Logcat.init("music")

            Logcat.d("Main", "file path--->${file.absolutePath}")
            player.start(file.absolutePath)
        }

    override fun initWithView() {
        view.startPlay.setOnClickListener {
            permission.launch(arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE))
        }
    }

    override fun initWithData() {
        view.version.text = player.getVersion()
    }
}