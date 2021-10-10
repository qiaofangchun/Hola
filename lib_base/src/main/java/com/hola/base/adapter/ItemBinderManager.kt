package com.hola.base.adapter

import android.util.SparseArray

class ItemBinderManager {
    private val itemType = SparseArray<String>()
    private val itemBinder = HashMap<String, ItemBinder<*, *>>()

    fun <T, VH : ViewHolder> register(clz: Class<T>, binder: ItemBinder<T, VH>) {
        val key = clz.name
        itemBinder[key] = binder
        itemType.put(binder.getItemViewType(), key)
    }

    fun <T> unregister(clazz: Class<T>) {
        val key = clazz.name
        val binder = itemBinder.remove(key)
        binder?.let { itemType.remove(it.getItemViewType()) }
    }

    fun getItemBinder(viewType: Int): ItemBinder<*, *> {
        return itemType.get(viewType)?.let {
            itemBinder[it]
        } ?: throw RuntimeException("Not Found ItemBinder, viewType:$viewType")
    }

    fun getItemBinder(clazz: Class<*>): ItemBinder<*, *> {
        val key = clazz.name
        return itemBinder[key]
            ?: throw RuntimeException("Not Found ItemBinder, class:${clazz.name}")
    }
}