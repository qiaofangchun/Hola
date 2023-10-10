package com.hola.app.music

import android.graphics.RenderEffect
import androidx.fragment.app.viewModels
import com.hola.app.music.databinding.FragmentMainBinding
import com.hola.base.fragment.BaseFragment
import com.hola.viewbind.viewBinding

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val view by viewBinding(FragmentMainBinding::bind)
    private val model by viewModels<MainViewModel>()
    private val adapter by lazy { MediaItemAdapter(requireContext()) }

    override fun initWithView() {

    }

    override fun initWithData() {

    }
}