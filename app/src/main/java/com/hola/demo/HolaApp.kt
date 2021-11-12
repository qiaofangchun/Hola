package com.hola.demo

import com.hola.base.application.BaseApplication
import com.hola.skin.SkinCompatManager
import com.hola.skin.helper.TextViewSkinHelper

class HolaApp : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        SkinCompatManager.init(this)
        SkinCompatManager.getInstance().addSkinHelper(TextViewSkinHelper())
    }
}