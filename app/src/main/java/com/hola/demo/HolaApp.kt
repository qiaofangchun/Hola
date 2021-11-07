package com.hola.demo

import com.hola.base.application.BaseApplication
import com.hola.skin.SkinManager

class HolaApp : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        SkinManager.init(this);
    }
}