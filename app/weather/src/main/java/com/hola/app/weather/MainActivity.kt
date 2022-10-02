package com.hola.app.weather

import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.hola.app.weather.databinding.ActivityMainBinding.bind
import com.hola.app.weather.location.AMapLocationClient
import com.hola.app.weather.location.SystemLocationClient
import com.hola.app.weather.ui.main.MainViewAction
import com.hola.app.weather.ui.main.MainViewModel
import com.hola.app.weather.ui.main.MainViewState
import com.hola.base.activity.BaseActivity
import com.hola.common.utils.Logcat
import com.hola.location.LocationHelper
import com.hola.location.annotation.ExecuteMode
import com.hola.viewbind.viewBinding
import java.util.logging.Level
import java.util.logging.Logger

class MainActivity : BaseActivity(R.layout.activity_main) {
    private val view by viewBinding(::bind)
    private val model by viewModels<MainViewModel>()

    override fun initWithView() {
        view.change.setOnClickListener {
            model.input(MainViewAction.SearchPlace("北京"))
            model.input(MainViewAction.Text())
            model.input(MainViewAction.Toast())
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
                is MainViewState.SearchPlace -> view.content.text = it.data ?: it.state.message
                is MainViewState.ToastState -> Toast.makeText(this,it.data ?: it.state.message,Toast.LENGTH_LONG).show()
                is MainViewState.TextState-> view.change.text = it.data ?: it.state.message
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
                if (map.containsValue(false) || map.containsValue(null)) {
                    return@registerForActivityResult
                }
            }
        permission.launch(LocationHelper.getPermissions())
    }
}