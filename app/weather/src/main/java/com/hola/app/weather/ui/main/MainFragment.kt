package com.hola.app.weather.ui.main

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.hola.app.weather.R
import com.hola.app.weather.databinding.FragmentMainBinding.bind
import com.hola.base.fragment.BaseFragment
import com.hola.ext.viewBinding

class MainFragment : BaseFragment(R.layout.fragment_main) {
    companion object {
        fun newInstance() = MainFragment()
    }

    private val view by viewBinding(::bind)

    override fun initWithView() {
        //view.recyclerView.layoutManager = MainLayoutManager()
        //view.recyclerView.addOnScrollListener(mScrollListener)
        //view.recyclerView.setOnTouchListener(indicatorStateListener)
    }

    override fun initWithData() {

    }
}