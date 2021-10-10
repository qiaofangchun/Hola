package com.hola.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

abstract class ItemBinder<T, VH : ViewHolder>(@LayoutRes private val resId: Int, private val clazz: Class<T>) {
    fun getItemViewType(): Int = resId

    fun getItemDataType(): Class<T> = clazz

    private fun onCreateView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(resId, parent, false)
    }

    fun onCreateViewHolder(parent: ViewGroup): VH {
        return onCreateViewHolder(onCreateView(parent)).apply { viewType = resId }
    }

    abstract fun onCreateViewHolder(itemView: View): VH

    abstract fun onBindViewHolder(holder: VH, data: T, position: Int)

    open fun onBindViewHolder(holder: VH, data: T, position: Int, payloads: MutableList<Any>) = Unit
}