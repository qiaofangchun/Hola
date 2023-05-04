package com.hola.app.music

import android.os.Environment
import android.support.v4.media.MediaBrowserCompat
import androidx.fragment.app.viewModels
import com.hola.app.music.databinding.FragmentMainBinding
import com.hola.base.activity.BaseFragment
import com.hola.common.utils.Logcat
import com.hola.viewbind.viewBinding
import java.io.File

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val view by viewBinding(FragmentMainBinding::bind)
    private val model by viewModels<MainViewModel>()
    private val adapter by lazy { MediaItemAdapter(requireContext()) }

    override fun initWithView() {
        view.mediaList.adapter = adapter
    }

    override fun initWithData() {
        val file = File(Environment.getExternalStorageDirectory(), "video.mp4")
        Logcat.d("Main", "file path--->${file.absolutePath}")
        model.input(MainViewAction.SubscribeMediaData("abcdefg"))
        model.output(this) {
            when (it) {
                is MainViewState.MediaData -> {
                    Logcat.d("Main", "media data--->${it.mediaData}")
                    adapter.setItems(it.mediaData)
                }
            }
        }
    }
}