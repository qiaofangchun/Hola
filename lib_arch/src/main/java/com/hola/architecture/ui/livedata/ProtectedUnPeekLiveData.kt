package com.hola.architecture.ui.livedata

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

open class ProtectedUnPeekLiveData<T> : LiveData<T>() {
    protected var isAllowNullValue = false
    private val observers = HashMap<Int, Boolean?>()

    fun observeInActivity(activity: AppCompatActivity, observer: Observer<in T>) {
        val owner: LifecycleOwner = activity
        val storeId = System.identityHashCode(activity.viewModelStore)
        observe(storeId, owner, observer)
    }

    fun observeInFragment(fragment: Fragment, observer: Observer<in T>) {
        val owner = fragment.viewLifecycleOwner
        val storeId = System.identityHashCode(fragment.viewModelStore)
        observe(storeId, owner, observer)
    }

    private fun observe(storeId: Int, owner: LifecycleOwner, observer: Observer<in T>) {
        observers[storeId] ?: run { observers[storeId] = true }
        super.observe(owner, { t: T ->
            observers[storeId]?.takeIf { !it }?.let {
                observers[storeId] = true
                if (t != null || isAllowNullValue) {
                    observer.onChanged(t)
                }
            }
        })
    }

    override fun setValue(value: T?) {
        if (value != null || isAllowNullValue) {
            observers.entries.forEach {
                it.setValue(false)
            }
            super.setValue(value)
        }
    }

    protected fun clear() {
        super.setValue(null)
    }
}