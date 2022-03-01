package com.hola.app.weather.widget.insets

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.hola.app.weather.widget.insets.FitBothSideBarView.Companion.SIDE_TOP


class FitSystemBarAppBarLayout : AppBarLayout, FitBothSideBarView {
    private var mHelper: FitBothSideBarHelper

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context)

    init {
        ViewCompat.setOnApplyWindowInsetsListener(this, null)
        mHelper = FitBothSideBarHelper(this, SIDE_TOP)
    }

    override fun fitSystemWindows(insets: Rect): Boolean {
        return mHelper.fitSystemWindows(insets, object : FitBothSideBarHelper.InsetsConsumer {
            override fun consume() {
                fitSystemBar()
            }
        })
    }

    private fun fitSystemBar() {
        setPadding(0, mHelper.top(), 0, 0)
    }

    override fun addFitSide(@FitBothSideBarView.FitSide side: Int) {
        // do nothing.
    }

    override fun removeFitSide(@FitBothSideBarView.FitSide side: Int) {
        // do nothing.
    }

    override fun setFitSystemBarEnabled(top: Boolean, bottom: Boolean) {
        mHelper.setFitSystemBarEnabled(top, bottom)
    }

    override fun getTopWindowInset(): Int = mHelper.top()

    override fun getBottomWindowInset(): Int = 0
}