package com.hola.app.weather.widget.insets

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.hola.app.weather.R
import com.hola.app.weather.widget.insets.FitBothSideBarView.FitSide

class FitSystemBarRecyclerView : RecyclerView, FitBothSideBarView {
    private var mHelper: FitBothSideBarHelper

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.FitSystemBarRecyclerView, defStyleAttr, 0
        )
        val fitSide = a.getInt(
            R.styleable.FitSystemBarRecyclerView_rv_side,
            FitBothSideBarView.SIDE_TOP or FitBothSideBarView.SIDE_BOTTOM
        )
        a.recycle()
        mHelper = FitBothSideBarHelper(this, fitSide)
    }

    override fun fitSystemWindows(insets: Rect): Boolean = mHelper.fitSystemWindows(insets)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setPadding(0, mHelper.top(), 0, mHelper.bottom())
    }

    override fun addFitSide(@FitSide side: Int) = mHelper.addFitSide(side)

    override fun removeFitSide(@FitSide side: Int) = mHelper.removeFitSide(side)

    override fun setFitSystemBarEnabled(top: Boolean, bottom: Boolean) =
        mHelper.setFitSystemBarEnabled(top, bottom)

    override fun getTopWindowInset(): Int = mHelper.top()

    override fun getBottomWindowInset(): Int = mHelper.bottom()


}