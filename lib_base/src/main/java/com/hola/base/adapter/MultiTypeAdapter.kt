package com.hola.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MultiTypeAdapter(var itemData: List<Any> = emptyList()) : RecyclerView.Adapter<ViewHolder>() {
    private val manager = ItemBinderManager()

    inline fun <reified T, VH : ViewHolder> registerType(itemBinder: ItemBinder<T, VH>): MultiTypeAdapter {
        registerType(T::class.java, itemBinder)
        return this
    }

    fun <T, VH : ViewHolder> registerType(dataClz: Class<T>, itemBinder: ItemBinder<T, VH>) {
        manager.register(dataClz, itemBinder)
    }

    fun <T> unregisterType(dataClz: Class<T>) {
        manager.unregister(dataClz)
    }

    override fun getItemViewType(position: Int): Int {
        return manager.getItemBinder(itemData[position]::class.java).getItemViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return manager.getItemBinder(viewType).onCreateViewHolder(parent)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binder = manager.getItemBinder(holder.viewType) as ItemBinder<Any, ViewHolder>
        binder.onBindViewHolder(holder, itemData[position], position)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        payloads.takeUnless { it.isEmpty() }?.let {
            val binder = manager.getItemBinder(holder.viewType) as ItemBinder<Any, ViewHolder>
            binder.onBindViewHolder(holder, itemData[position], position, payloads)
        } ?: let {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int {
        return itemData.size
    }
}