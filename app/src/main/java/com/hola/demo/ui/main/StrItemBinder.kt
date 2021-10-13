package com.hola.demo.ui.main

import android.view.View
import android.widget.TextView
import com.hola.base.adapter.ItemBinder
import com.hola.base.adapter.ViewHolder
import com.hola.demo.R

class StrItemBinder : ItemBinder<String, ViewHolder>(R.layout.item_string_view, String::class.java) {
    override fun onCreateViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, data: String, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.content).text = data
    }
}