package com.hola.app.music

import android.graphics.BitmapFactory
import android.os.SystemClock
import androidx.fragment.app.viewModels
import com.hola.app.music.databinding.FragmentMainBinding
import com.hola.base.fragment.BaseFragment
import com.hola.common.utils.Logcat
import com.hola.lib.blur.BlurToolkit
import com.hola.viewbind.viewBinding

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val view by viewBinding(FragmentMainBinding::bind)
    private val model by viewModels<MainViewModel>()
    private val adapter by lazy { MediaItemAdapter(requireContext()) }

    override fun initWithView() {
        val startTime = System.currentTimeMillis()
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.bg)
        view.blur.setImageBitmap(BlurToolkit.blur(bitmap,5))
        Logcat.i("aaaa","-------->${System.currentTimeMillis()-startTime}")
    }

    override fun initWithData() {

    }
}