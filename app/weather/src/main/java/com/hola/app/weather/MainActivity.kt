package com.hola.app.weather

import android.content.pm.ActivityInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.hola.app.weather.databinding.ActivityMainBinding.bind
import com.hola.app.weather.ui.main.MainViewModel
import com.hola.app.weather.utils.LocationHelper
import com.hola.base.activity.BaseActivity
import com.hola.ext.viewBinding

class MainActivity : BaseActivity(R.layout.activity_main) {
    private val view by viewBinding(::bind)
    private val model by viewModels<MainViewModel>()

    override fun requestedOrientation() = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    override fun initWithView() {
        view.change.setOnClickListener {
            model.searchPlace()
        }
    }

    override fun initWithData() {
        val permission =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
                if (map.containsValue(false) || map.containsValue(null)) {
                    return@registerForActivityResult
                }
            }
        permission.launch(LocationHelper.getPermissions())
    }
}