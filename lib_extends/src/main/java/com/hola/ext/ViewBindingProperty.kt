package com.hola.ext

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// -------------------------------------------------------------------------------------
// ViewBindingProperty
// -------------------------------------------------------------------------------------

private const val TAG = "ViewBindingProperty"

interface ViewBindingProperty<in R : Any, out V : ViewBinding> : ReadOnlyProperty<R, V> {
    @MainThread
    fun clear()
}

abstract class LifecycleViewBindingProperty<in R : Any, out V : ViewBinding>(
    private val viewBinder: (R) -> V
) : ViewBindingProperty<R, V> {

    private var viewBinding: V? = null

    protected abstract fun getLifecycleOwner(thisRef: R): LifecycleOwner

    @MainThread
    override fun getValue(thisRef: R, property: KProperty<*>): V {
        return viewBinding ?: let {
            getLifecycleOwner(thisRef).lifecycle.takeIf { it.currentState != Lifecycle.State.DESTROYED }
                ?.addObserver(ClearOnDestroyLifecycleObserver(this))
            viewBinder(thisRef)
        }
    }

    @MainThread
    override fun clear() {
        viewBinding = null
    }

    private class ClearOnDestroyLifecycleObserver(
        private val property: LifecycleViewBindingProperty<*, *>
    ) : LifecycleEventObserver {

        private companion object {
            private val mainHandler = Handler(Looper.getMainLooper())
        }

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            event.takeIf { it == Lifecycle.Event.ON_DESTROY }
                ?.let { mainHandler.post { property.clear() } }
        }
    }
}

// -------------------------------------------------------------------------------------
// Utils
// -------------------------------------------------------------------------------------

fun <V : View> View.requireViewByIdCompat(@IdRes id: Int): V {
    return ViewCompat.requireViewById(this, id)
}

fun <V : View> Activity.requireViewByIdCompat(@IdRes id: Int): V {
    return ActivityCompat.requireViewById(this, id)
}
