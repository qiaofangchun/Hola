package com.hola.base.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes resId: Int) : super(resId)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initWithView()
        initWithData()
    }

    protected abstract fun initWithView()

    protected abstract fun initWithData()

}