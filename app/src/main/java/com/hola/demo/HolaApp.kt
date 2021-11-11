package com.hola.demo

import com.hola.base.application.BaseApplication
import com.hola.skin.SkinManager
import com.hola.skin.helper.TextViewSkinHelper

class HolaApp : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        SkinManager.init(this)
        SkinManager.getInstance().addSkinHelper(TextViewSkinHelper())
        SkinManager.getInstance().addSkinHelper(RvSkinHelper())
    }
}