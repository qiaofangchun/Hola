package com.hola.demo.ui.main

import android.util.ArrayMap
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hola.base.adapter.MultiTypeAdapter
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

    private val viewModel:MainViewModel by viewModels()

    override fun initWithView() {
        binding.content.layoutManager = LinearLayoutManager(context)
        binding.content.adapter = MultiTypeAdapter().apply {
            val itemData = ArrayList<Any>()
            itemData.add("String.1")
            itemData.add(1)
            itemData.add("String.2")
            itemData.add(2)
            itemData.add("String.3")
            itemData.add(3)
            itemData.add("String.4")
            itemData.add(4)
            itemData.add("String.5")
            itemData.add(5)
            itemData.add("String.6")
            itemData.add(6)
            itemData.add("String.7")
            itemData.add(7)
            itemData.add("String.8")
            itemData.add(8)
            itemData.add("String.9")
            itemData.add(9)
            registerType(NumItemBinder())
            registerType(StrItemBinder())
            this.itemData = itemData
            notifyDataSetChanged()
        }
    }

    override fun initWithData() {

    }
}