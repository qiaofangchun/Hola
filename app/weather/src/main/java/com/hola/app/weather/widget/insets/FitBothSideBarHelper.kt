package com.hola.app.weather.widget.insets

import android.graphics.Rect
import android.view.View
import com.hola.app.weather.widget.insets.FitBothSideBarView.FitSide

class FitBothSideBarHelper(
    private var mTarget: View,
    @FitSide private var mFitSide: Int,
    private var mFitTopSideEnabled: Boolean,
    private var mFitBottomSideEnabled: Boolean
) {
    private val sRootInsetsCache = ThreadLocal<Rect>()
    private var mWindowInsets = Rect(0, 0, 0, 0)
    private val insetsConsumer = object : InsetsConsumer {
        override fun consume() {
            mTarget.requestLayout()
        }
    }

    constructor(target: View) : this(
        target,
        FitBothSideBarView.SIDE_TOP or FitBothSideBarView.SIDE_BOTTOM
    )

    constructor(target: View, fitSide: Int) : this(target, fitSide, true, true)

    fun setRootInsetsCache(rootInsets: Rect) {
        sRootInsetsCache.set(rootInsets)
    }

    fun fitSystemWindows(r: Rect): Boolean = fitSystemWindows(r, insetsConsumer)

    fun fitSystemWindows(r: Rect, consumer: InsetsConsumer): Boolean {
        mWindowInsets.set(r)
        consumer.consume()
        return false
    }

    fun getWindowInsets(): Rect = sRootInsetsCache.get()?.let {
        sRootInsetsCache.get()
    } ?: mWindowInsets

    fun left(): Int = getWindowInsets().left

    fun top(): Int {
        return if (mFitSide and FitBothSideBarView.SIDE_TOP != 0 && mFitTopSideEnabled) getWindowInsets().top else 0
    }

    fun right(): Int = getWindowInsets().right

    fun bottom(): Int {
        return if (mFitSide and FitBothSideBarView.SIDE_BOTTOM != 0 && mFitBottomSideEnabled) getWindowInsets()!!.bottom else 0
    }

    fun addFitSide(@FitSide side: Int) {
        if (mFitSide and side != 0) {
            mFitSide = mFitSide or side
            mTarget.requestLayout()
        }
    }

    fun removeFitSide(@FitSide side: Int) {
        if (mFitSide and side != 0) {
            mFitSide = mFitSide xor side
            mTarget.requestLayout()
        }
    }

    fun setFitSystemBarEnabled(top: Boolean, bottom: Boolean) {
        if (mFitTopSideEnabled != top || mFitBottomSideEnabled != bottom) {
            mFitTopSideEnabled = top
            mFitBottomSideEnabled = bottom
            mTarget.requestLayout()
        }
    }

    interface InsetsConsumer {
        fun consume()
    }
}