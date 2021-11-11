package com.hola.demo

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.hola.base.activity.BaseActivity
import com.hola.demo.databinding.MainActivityBinding
import com.hola.demo.ui.main.MainFragment
import com.hola.ext.viewBinding
import com.hola.skin.SkinCompatDelegate
import java.io.File

class MainActivity : BaseActivity(R.layout.main_activity) {
    companion object {
        private const val TAG = "MainActivity"
    }

    private var skinDelegate = SkinCompatDelegate(this, true)
    private val binding by viewBinding(MainActivityBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        skinDelegate.installFactory2()
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commitNow()
        }
        val data = MutableLiveData<String>()

        data.observe(this) {

        }
        binding.btnChange.setOnClickListener {
            val path = Environment.getExternalStorageDirectory().absolutePath+File.separator+"skin.skin"
            skinDelegate.useDynamicSkin(path, R.color.teal_700)
        }
    }

    override fun initWithView() {

    }

    override fun initWithData() {

    }
}