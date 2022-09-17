package com.hola.app.weather.ui.main

import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.hola.app.weather.R
import com.hola.app.weather.databinding.FragmentMainBinding.bind
import com.hola.base.fragment.BaseFragment
import com.hola.viewbind.viewBinding

class MainFragment : BaseFragment(R.layout.fragment_main) {
    companion object {
        fun newInstance() = MainFragment()
    }

    private val view by viewBinding(::bind)
    private val model by activityViewModels<MainViewModel>()

    override fun initWithView() {
        //view.recyclerView.layoutManager = MainLayoutManager()
        //view.recyclerView.addOnScrollListener(mScrollListener)
        //view.recyclerView.setOnTouchListener(indicatorStateListener)
    }

    override fun initWithData() {

    }
}