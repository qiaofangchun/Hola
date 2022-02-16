package com.hola.app.weather.widget.weather.drawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import com.hola.app.weather.widget.weather.CalculateUtils
import com.hola.app.weather.widget.weather.holder.SunnyHolder
import java.util.ArrayList

/**
 * 晴天
 * @author Mixiaoxiao
 */
class SunnyDrawer(context: Context) : BaseDrawer(context, false) {
    companion object {
        private const val SUNNY_COUNT = 3
        private val drawableColors = intArrayOf(0x20ffffff, 0x10ffffff)
    }

    //	private SunnyHolder holder;
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val holders = ArrayList<SunnyHolder>()
    private val drawable = GradientDrawable(GradientDrawable.Orientation.BL_TR, drawableColors)

    init {
        //		drawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[] { 0x10ffffff, 0x20ffffff });
        drawable.shape = GradientDrawable.OVAL
        drawable.gradientType = GradientDrawable.RADIAL_GRADIENT
        //		drawable.setGradientRadius((float) (Math.sqrt(2) * 60));
        paint.color = 0x33ffffff
    }

    override fun drawWeather(canvas: Canvas, alpha: Float): Boolean {
        val size = width * centerOfWidth
        for (holder in holders) {
            holder.updateRandom(drawable, alpha)
            drawable.draw(canvas)
        }
        paint.color = Color.argb((alpha * 0.18f * 255f).toInt(), 0xff, 0xff, 0xff)
        canvas.drawCircle(size, size, width * 0.12f, paint)
        return true
    }

    private val centerOfWidth = 0.02f

    //private static final float SUNNY_MIN_SIZE = 60f;// dp
    //private static final float SUNNY_MAX_SIZE = 500f;// dp
    override fun setSize(width: Int, height: Int) {
        super.setSize(width, height)
        if (holders.size == 0) {
            val minSize = width * 0.16f //dp2px(SUNNY_MIN_SIZE);
            val maxSize = width * 1.5f //dp2px(SUNNY_MAX_SIZE);
            val center = width * centerOfWidth
            val deltaSize = (maxSize - minSize) / SUNNY_COUNT
            for (i in 0 until SUNNY_COUNT) {
                val curSize = maxSize - i * deltaSize * CalculateUtils.getRandom(0.9f, 1.1f)
                val holder = SunnyHolder(center, center, curSize, curSize)
                holders.add(holder)
            }
        }
        //		if(this.holder == null){
//			final float center = width * 0.25f;
//			final float size = width * 0.3f;
//			holder = new SunnyHolder(center, center, size, size);
//		}
    }
}