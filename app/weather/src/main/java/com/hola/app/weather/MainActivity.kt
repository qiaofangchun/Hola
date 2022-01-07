package com.hola.app.weather

import android.os.Bundle
import androidx.activity.viewModels
import com.hola.app.weather.databinding.ActivityMainBinding.bind
import com.hola.base.activity.BaseActivity
import com.hola.ext.viewBinding
import java.util.*

class MainActivity : BaseActivity(R.layout.activity_main) {
    private val viewBinding by viewBinding(::bind)
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initWithView() {
        viewBinding.text.setOnClickListener {
            viewModel.searchPlace("北京", "zh_CN")
        }
    }

    override fun initWithData() {

    }
}