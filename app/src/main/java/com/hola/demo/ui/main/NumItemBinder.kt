package com.hola.demo.ui.main

import android.view.View
import android.widget.TextView
import com.hola.base.adapter.ItemBinder
import com.hola.base.adapter.ViewHolder
import com.hola.demo.R
import com.hola.demo.databinding.ItemNumberViewBinding
import com.hola.ext.viewBinding

class NumItemBinder : ItemBinder<Int, NumItemBinder.Holder>(R.layout.item_number_view, Int::class.javaObjectType) {
    override fun onCreateViewHolder(itemView: View): Holder {
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, data: Int, position: Int) {
        holder.viewBind.content.text = "Num:$data"
    }

    class Holder(view:View):ViewHolder(view){
        val viewBind by viewBinding(ItemNumberViewBinding::bind)
    }
}