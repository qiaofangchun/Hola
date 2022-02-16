package com.hola.app.music

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hola.app.music.databinding.ActivityMainBinding.bind
import com.hola.ext.viewBinding

class MainActivity : AppCompatActivity() {
    private val view by viewBinding(::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}