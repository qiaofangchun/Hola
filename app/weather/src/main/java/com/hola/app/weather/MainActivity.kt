package com.hola.app.weather

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hola.app.weather.databinding.ActivityMainBinding.bind
import com.hola.app.weather.location.AMapLocationClient
import com.hola.app.weather.location.SystemLocationClient
import com.hola.app.weather.ui.main.MainViewAction
import com.hola.app.weather.ui.main.MainViewModel
import com.hola.app.weather.ui.main.MainViewState
import com.hola.base.activity.BaseActivity
import com.hola.location.Location
import com.hola.location.LocationHelper
import com.hola.location.annotation.ExecuteMode
import com.hola.viewbind.viewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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
            Log.i("qfc", "output()")
            when (it) {
                is MainViewState.SearchPlace -> view.content.text = it.data ?: it.state.message
                is MainViewState.ToastState -> Toast.makeText(this,it.data ?: it.state.message,Toast.LENGTH_LONG).show()
                is MainViewState.TextState-> view.change.text = it.data ?: it.state.message
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