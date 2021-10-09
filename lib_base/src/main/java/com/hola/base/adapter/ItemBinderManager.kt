package com.hola.base.adapter

import android.util.SparseArray

class ItemBinderManager {
    private val itemType = SparseArray<String>()
    private val itemBinder = HashMap<String, ItemBinder<*>>()

    fun register(binder: ItemBinder<*>) {
        val key = binder.getItemDataType().name
        itemBinder[key] = binder
        itemType.put(binder.getItemViewType(), key)
    }

    fun unregister(clazz: Class<*>) {
        val key = clazz.name
        val binder = itemBinder.remove(key)
        binder?.let { itemType.remove(it.getItemViewType()) }
    }

    fun getItemBinder(viewType: Int): ItemBinder<*> {
        return itemType.get(viewType)?.let {
            itemBinder[it]
        } ?: throw RuntimeException("Not Found ItemBinder, viewType:$viewType")
    }

    fun getItemBinder(clazz: Class<*>): ItemBinder<*> {
        val key = clazz.name
        return itemBinder[key]
            ?: throw RuntimeException("Not Found ItemBinder, class:${clazz.name}")
    }
}