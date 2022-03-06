package com.hola.base.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes resId: Int) : super(resId)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initWithView()
        registerListener()
        initWithData()
    }

    override fun onDestroy() {
        unregisterListener()
        super.onDestroy()
    }

    protected abstract fun initWithView()

    protected abstract fun initWithData()

    protected fun registerListener() {}

    protected fun unregisterListener() {}
}