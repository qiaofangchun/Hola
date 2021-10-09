package com.hola.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

abstract class ItemBinder<T>(@LayoutRes private val resId: Int, private val clazz: Class<T>) {
    fun getItemViewType(): Int = resId

    fun getItemDataType(): Class<T> = clazz

    private fun onCreateView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(resId, parent, false)
    }

    fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return onCreateViewHolder(onCreateView(parent)).apply { viewType = resId }
    }

    abstract fun onCreateViewHolder(itemView: View): ViewHolder

    abstract fun onBindViewHolder(holder: ViewHolder, data: T, position: Int)

    open fun onBindViewHolder(holder: ViewHolder, data: T, position: Int, payloads: MutableList<Any>) = Unit
}