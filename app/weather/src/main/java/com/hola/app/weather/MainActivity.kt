package com.hola.app.weather

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.hola.app.weather.databinding.ActivityMainBinding.bind
import com.hola.app.weather.location.AMapLocationClient
import com.hola.app.weather.location.SystemLocationClient
import com.hola.app.weather.ui.main.MainViewModel
import com.hola.base.activity.BaseActivity
import com.hola.location.LocationHelper
import com.hola.location.annotation.ExecuteMode
import com.hola.viewbind.viewBinding

class MainActivity : BaseActivity(R.layout.activity_main) {
    private val view by viewBinding(::bind)
    private val model by viewModels<MainViewModel>()

    override fun initWithView() {
        view.change.setOnClickListener {
            val intent = Intent().apply {
                action = "hola.intent.action.MUSIC"
                `package` = "com.hola.app.music"
            }
            /*bindService(intent, object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    Log.d("qfc","----->onServiceConnected()")
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    Log.d("qfc","----->onServiceDisconnected()")
                }

            }, BIND_AUTO_CREATE)*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
    }

    override fun initWithData() {
        LocationHelper.init(
            ExecuteMode.SEPARATE,
            true,
            arrayOf(AMapLocationClient(this), SystemLocationClient(this))
        )
        val permission =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
                if (map.containsValue(false) || map.containsValue(null)) {
                    return@registerForActivityResult
                }
            }
        permission.launch(LocationHelper.getPermissions())
    }
}