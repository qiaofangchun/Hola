package com.hola.app.music

import android.graphics.Color
import android.os.Bundle
import com.hola.app.music.databinding.ActivityMainBinding
import com.hola.base.activity.BaseActivity
import com.hola.viewbind.viewBinding
import jp.wasabeef.blurry.Blurry

class MainActivity : BaseActivity(R.layout.activity_main) {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val view by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commitNow()
        }
    }

    override fun initWithView() {
        Blurry.with(this)
            .radius(100)
            .sampling(8)
            .color(Color.BLUE)
            .async()
            .animate(500)
            .onto(view.bottomView.miniPlayer);

        /*val radius = 20f
        val decorView: View = window.decorView
        val windowBackground: Drawable = decorView.background
        view.miniPlayer.blurViewPlay.setupWith(view.container)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setHasFixedTransformationMatrix(true)*/
    }

    override fun initWithData() {

    }
}