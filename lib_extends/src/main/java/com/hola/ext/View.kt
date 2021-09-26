package com.hola.ext

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty

fun <T : View?> View.findViewById(@IdRes id: Int): T = this.findViewById(id)

inline fun <V : ViewBinding> ViewGroup.viewBinding(
    crossinline viewBinder: (View) -> V,
    crossinline viewProvider: (ViewGroup) -> View = { this }
): ViewBindingProperty<ViewGroup, V> = LazyViewBindingProperty { viewGroup: ViewGroup ->
    viewBinder(viewProvider(viewGroup))
}

inline fun <V : ViewBinding> ViewGroup.viewBinding(
    crossinline viewBinder: (View) -> V,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<ViewGroup, V> = LazyViewBindingProperty { viewGroup: ViewGroup ->
    viewBinder(viewGroup.requireViewByIdCompat(viewBindingRootId))
}


inline fun <V : ViewBinding> RecyclerView.ViewHolder.viewBinding(
    crossinline viewBinder: (View) -> V,
    crossinline viewProvider: (RecyclerView.ViewHolder) -> View = RecyclerView.ViewHolder::itemView
): ViewBindingProperty<RecyclerView.ViewHolder, V> =
    LazyViewBindingProperty { holder: RecyclerView.ViewHolder ->
        viewBinder(viewProvider(holder))
    }

inline fun <V : ViewBinding> RecyclerView.ViewHolder.viewBinding(
    crossinline viewBinder: (View) -> V,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<RecyclerView.ViewHolder, V> =
    LazyViewBindingProperty { holder: RecyclerView.ViewHolder ->
        viewBinder(holder.itemView.requireViewByIdCompat(viewBindingRootId))
    }

class LazyViewBindingProperty<in R : Any, out V : ViewBinding>(
    private val viewBinder: (R) -> V
) : ViewBindingProperty<R, V> {

    private var viewBinding: V? = null

    @Suppress("UNCHECKED_CAST")
    @MainThread
    override fun getValue(thisRef: R, property: KProperty<*>): V {
        return viewBinding ?: viewBinder(thisRef).also {
            this.viewBinding = it
        }
    }

    @MainThread
    override fun clear() {
        viewBinding = null
    }
}