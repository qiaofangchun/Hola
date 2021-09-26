package com.hola.ext

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

@Suppress("UNCHECKED_CAST")
inline fun <F : Fragment, V : ViewBinding> Fragment.viewBinding(
    crossinline viewBinder: (View) -> V,
    @IdRes resId: Int
): ViewBindingProperty<F, V> = when (this) {
    is DialogFragment -> viewBinding(viewBinder) { fragment: DialogFragment ->
        fragment.getRootView(resId)
    } as ViewBindingProperty<F, V>
    else -> viewBinding(viewBinder) { fragment: F ->
        fragment.requireView().requireViewByIdCompat(resId)
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <F : Fragment, V : ViewBinding> Fragment.viewBinding(
    crossinline viewBinder: (View) -> V,
    crossinline viewProvider: (F) -> View = Fragment::requireView
): ViewBindingProperty<F, V> = when (this) {
    is DialogFragment -> DialogFragmentViewBindingProperty { fragment: F ->
        viewBinder(viewProvider(fragment))
    } as ViewBindingProperty<F, V>
    else -> FragmentViewBindingProperty { fragment: F ->
        viewBinder(viewProvider(fragment))
    }
}

class FragmentViewBindingProperty<in F : Fragment, out V : ViewBinding>(
    viewBinder: (F) -> V
) : LifecycleViewBindingProperty<F, V>(viewBinder) {

    override fun getLifecycleOwner(thisRef: F): LifecycleOwner {
        try {
            return thisRef.viewLifecycleOwner
        } catch (ignored: IllegalStateException) {
            error("Fragment doesn't have view associated with it or the view has been destroyed")
        }
    }
}

class DialogFragmentViewBindingProperty<in F : DialogFragment, out V : ViewBinding>(
    viewBinder: (F) -> V
) : LifecycleViewBindingProperty<F, V>(viewBinder) {

    override fun getLifecycleOwner(thisRef: F): LifecycleOwner {
        return thisRef.takeIf { it.showsDialog }?:let {
            try {
                thisRef.viewLifecycleOwner
            } catch (ignored: IllegalStateException) {
                error("Fragment doesn't have view associated with it or the view has been destroyed")
            }
        }
    }
}