package com.hola.demo.ui.main

import android.view.View
import android.widget.TextView
import com.hola.base.adapter.ItemBinder
import com.hola.base.adapter.ViewHolder
import com.hola.demo.R

class NumItemBinder : ItemBinder<Int>(R.layout.item_number_view, Int::class.javaObjectType) {
    override fun onCreateViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, data: Int, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.content).text = "Num:$data"
    }
}