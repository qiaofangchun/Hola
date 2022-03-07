package com.hola.app.weather

import android.Manifest
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.hola.app.weather.databinding.ActivityMainBinding.bind
import com.hola.app.weather.location.*
import com.hola.app.weather.repository.locale.WeatherDb
import com.hola.app.weather.ui.main.MainViewModel
import com.hola.app.weather.utils.LocationHelper
import com.hola.app.weather.widget.weather.WeatherType
import com.hola.base.activity.BaseActivity
import com.hola.common.utils.AppHelper
import com.hola.ext.viewBinding

class MainActivity : BaseActivity(R.layout.activity_main) {
    private val view by viewBinding(::bind)
    private val model: MainViewModel by viewModels()
    private var type = WeatherType.DEFAULT

    override fun requestedOrientation() = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    override fun initWithView() {
        view.weatherView.setPadding(0, AppHelper.getStatusBarHeight(), 0, 0)
        view.weatherView.setDrawerType(type)
        view.weatherView.animation = AlphaAnimation(0f, 1f).apply {
            duration = 260
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    window.setBackgroundDrawable(ColorDrawable(Color.BLACK))
                }

                override fun onAnimationRepeat(animation: Animation) = Unit
                override fun onAnimationEnd(animation: Animation) = Unit
            })
        }
        view.change.setOnClickListener {
            model.getLocation()
            type = when (type) {
                WeatherType.DEFAULT -> WeatherType.CLEAR_D
                WeatherType.CLEAR_D -> WeatherType.RAIN_D
                WeatherType.RAIN_D -> WeatherType.SNOW_D
                WeatherType.SNOW_D -> WeatherType.CLOUDY_D
                WeatherType.CLOUDY_D -> WeatherType.OVERCAST_D
                WeatherType.OVERCAST_D -> WeatherType.FOG_D
                WeatherType.FOG_D -> WeatherType.HAZE_D
                WeatherType.HAZE_D -> WeatherType.SAND_D
                WeatherType.SAND_D -> WeatherType.WIND_D
                WeatherType.WIND_D -> WeatherType.RAIN_SNOW_D
                else -> WeatherType.UNKNOWN_D
            }
            view.weatherView.setDrawerType(type)
        }
    }

    override fun initWithData() {
        val permission =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
                if (map.containsValue(false) || map.containsValue(null)) {
                    return@registerForActivityResult
                }
                model.searchPlace()
            }
        permission.launch(LocationHelper.getPermissions())
    }

    override fun onResume() {
        super.onResume()
        view.weatherView.onResume()
    }

    override fun onPause() {
        view.weatherView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        view.weatherView.onDestroy()
        super.onDestroy()
    }
}