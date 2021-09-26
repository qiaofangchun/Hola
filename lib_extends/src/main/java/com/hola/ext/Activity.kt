package com.hola.ext

import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

inline fun <V : ViewBinding> ComponentActivity.viewBinding(
    crossinline viewBinder: (View) -> V,
    crossinline viewProvider: (ComponentActivity) -> View = ::findRootView
): ViewBindingProperty<ComponentActivity, V> = ActivityViewBindingProperty { activity: ComponentActivity ->
    viewBinder(viewProvider(activity))
}

inline fun <V : ViewBinding> ComponentActivity.viewBinding(
    crossinline viewBinder: (View) -> V,
    @IdRes resId: Int
): ViewBindingProperty<ComponentActivity, V> = ActivityViewBindingProperty { activity: ComponentActivity ->
    viewBinder(activity.requireViewByIdCompat(resId))
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
class ActivityViewBindingProperty<in A : ComponentActivity, out V : ViewBinding>(
    viewBinder: (A) -> V
) : LifecycleViewBindingProperty<A, V>(viewBinder) {

    override fun getLifecycleOwner(thisRef: A): LifecycleOwner {
        return thisRef
    }
}