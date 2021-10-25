package com.hola.demo

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hola.base.activity.BaseActivity
import com.hola.demo.databinding.MainActivityBinding
import com.hola.demo.ui.main.MainFragment
import com.hola.ext.viewBinding

class MainActivity : BaseActivity(R.layout.main_activity) {
    companion object{
        private const val TAG = "MainActivity"
    }
    private val binding by viewBinding(MainActivityBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment())
                    .commitNow()
        }
        val data = MutableLiveData<String>()

        data.observe(this){

        }

        val viewModelFactory = viewModelStore.clear()
    }

    override fun initWithView() {
        binding.layName.text = TAG
    }

    override fun initWithData() {

    }
}