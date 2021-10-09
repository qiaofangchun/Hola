package com.hola.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes private val resId: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(resId, container, false)
    }

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