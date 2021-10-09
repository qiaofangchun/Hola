package com.hola.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MultiTypeAdapter(var itemData: List<Any> = emptyList()) : RecyclerView.Adapter<ViewHolder>() {
    private val manager = ItemBinderManager()

    fun addItemBinder(itemBinder: ItemBinder<*>) {
        manager.register(itemBinder)
    }

    fun removeItemBinder(dataClazz: Class<*>) {
        manager.unregister(dataClazz)
    }

    override fun getItemViewType(position: Int): Int {
        return manager.getItemBinder(itemData[position]::class.java).getItemViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return manager.getItemBinder(viewType).onCreateViewHolder(parent)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binder = manager.getItemBinder(holder.viewType) as ItemBinder<Any>
        binder.onBindViewHolder(holder, itemData[position], position)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        payloads.takeUnless { it.isEmpty() }?.let {
            val binder = manager.getItemBinder(holder.viewType) as ItemBinder<Any>
            binder.onBindViewHolder(holder, itemData[position], position, payloads)
        } ?: let {
            super.onBindViewHolder(holder, position, payloads)
        }
    }



    override fun getItemCount(): Int {
        return itemData.size
    }
}