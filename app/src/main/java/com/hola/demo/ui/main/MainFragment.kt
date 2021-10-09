package com.hola.demo.ui.main

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
            addItemBinder(NumItemBinder())
            addItemBinder(StrItemBinder())
            this.itemData = itemData
            notifyDataSetChanged()
        }
    }

    override fun initWithData() {

    }
}