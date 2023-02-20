package com.hola.app.weather

import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.hola.app.weather.databinding.ActivityMainBinding.bind
import com.hola.app.weather.location.AMapLocationClient
import com.hola.app.weather.location.SystemLocationClient
import com.hola.app.weather.ui.main.MainViewAction
import com.hola.app.weather.ui.main.MainViewModel
import com.hola.app.weather.ui.main.MainViewState
import com.hola.base.activity.BaseActivity
import com.hola.common.utils.AppHelper
import com.hola.common.utils.Logcat
import com.hola.location.LocationHelper
import com.hola.location.annotation.ExecuteMode
import com.hola.viewbind.viewBinding

class MainActivity : BaseActivity(R.layout.activity_main) {
    private val view by viewBinding(::bind)
    private val model by viewModels<MainViewModel>()

    override fun initWithView() {
        view.change.setOnClickListener {
            model.input(MainViewAction.SearchPlace("北京"))
            /*bindService(Intent().apply {
                action = "hola.intent.action.MUSIC"
                `package` = "com.hola.app.music"
            }, object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    Log.i("qfc", "onServiceConnected()")
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    Log.i("qfc", "onServiceDisconnected()")
                }
            }, BIND_AUTO_CREATE)*/
        }
        model.output(this) {
            Logcat.i("qfc", "state=${it::class.java}")
            when (it) {
                is MainViewState.GetWeather -> view.content.text = it.data ?: it.state.message
            }
        }
    }

    override fun initWithData() {
        LocationHelper.init(
            ExecuteMode.CONCURRENT,
            true,
            arrayOf(SystemLocationClient(this), AMapLocationClient(this))
        )
        val permission =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
                if (map.containsValue(false)) {
                    return@registerForActivityResult
                }
            }
        permission.launch(LocationHelper.getPermissions())
    }
}