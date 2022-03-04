package com.hola.app.weather

import android.Manifest
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.hola.app.weather.databinding.ActivityMainBinding.bind
import com.hola.app.weather.location.AMapLocationClient
import com.hola.app.weather.location.Location
import com.hola.app.weather.location.LocationListener
import com.hola.app.weather.ui.main.MainViewModel
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
            model.searchPlace()
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
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { map ->
                //val coarse = map[Manifest.permission.ACCESS_COARSE_LOCATION]
                //val fine = map[Manifest.permission.ACCESS_FINE_LOCATION]
                if (map) {
                    startLocation()
                }
            }
        permission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun startLocation() {
        val client = AMapLocationClient().apply {
            //onceLocation(true)
            setLocationListener(object : LocationListener {
                override fun onSuccess(loc: Location) {
                    Log.d("qfc", "------->$loc")
                }

                override fun onFailure(exception: Exception) {
                    Log.d("qfc", "------->${exception.message}")
                }
            })
        }
        client.startLocation()
    }

    override fun onResume() {
        super.onResume()
        view.weatherView.onResume()
    }

    override fun onPause() {
        super.onPause()
        view.weatherView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        view.weatherView.onDestroy()
    }
}