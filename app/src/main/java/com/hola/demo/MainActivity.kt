package com.hola.demo

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.hola.demo.databinding.MainActivityBinding
import com.hola.demo.ui.main.MainFragment
import com.hola.ext.viewBinding
import com.hola.skin.SkinDelegate

class MainActivity : AppCompatActivity(R.layout.main_activity) {
    companion object {
        private const val TAG = "MainActivity"
    }

    private var skinDelegate = SkinDelegate(this, true)
    private val binding by viewBinding(MainActivityBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        skinDelegate.changeInflaterFactory2()
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
            skinDelegate.useDynamicSkin(null, 0)
        }
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        Log.d(TAG, "viewName--->$name")
        return skinDelegate.skinViewMatch(context, name, attrs)
    }

    /* override fun initWithView() {
         binding.layName.text = TAG
     }

     override fun initWithData() {

     }*/
}