package com.hola.demo.ui.main

import androidx.recyclerview.widget.LinearLayoutManager
import com.hola.base.fragment.BaseFragment
import com.hola.common.datastore.DataStore
import com.hola.demo.R
import com.hola.demo.databinding.MainFragmentBinding
import com.hola.ext.viewBinding

class MainFragment : BaseFragment(R.layout.main_fragment) {
    companion object {
        private const val TAG = "MainFragment"
    }

    private val binding by viewBinding(MainFragmentBinding::bind)

    override fun initWithView() {
        binding.content.layoutManager = LinearLayoutManager(context)
        binding.content.adapter = null
    }

    override fun initWithData() {

    }
}