package com.hola.architecture.ui.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class UnPeekLiveData<T>() : ProtectedUnPeekLiveData<T>() {
    @Deprecated("",)
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        throw IllegalArgumentException(
            "Taking into account the normal permission of preventing backflow logic, do not use observeForever to communicate between pages. Instead, you can use ObserveInActivity and ObserveInFragment methods to observe in Activity and Fragment respectively."
        )
    }

    @Deprecated("")
    override fun observeForever(observer: Observer<in T>) {
        throw IllegalArgumentException(
            ("Considering avoid lifecycle security issues, do not use observeForever for communication between pages.")
        )
    }

    class Builder<T>() {
        private var isAllowNullValue = false
        fun setAllowNullValue(allowNullValue: Boolean): Builder<T> {
            isAllowNullValue = allowNullValue
            return this
        }

        fun create(): UnPeekLiveData<T> {
            val liveData = UnPeekLiveData<T>()
            liveData.isAllowNullValue = isAllowNullValue
            return liveData
        }
    }
}