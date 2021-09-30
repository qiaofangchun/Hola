package com.hola.common.manager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.hola.common.callback.ActivityLifecycleCallbacks
import java.util.*

object ActivityManager {
    private val stack = Stack<Activity>()

    fun init(app: Application) {
        app.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks() {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                activity?.let { addActivity(it) }
            }

            override fun onActivityDestroyed(activity: Activity?) {
                activity?.let { removeActivity(it) }
            }
        })
    }

    fun addActivity(activity: Activity) {
        stack.add(0, activity)
    }

    fun removeActivity(activity: Activity) {
        stack.remove(activity)
    }

    fun isStackTop(activity: Activity): Boolean {
        return stack.takeIf { it.contains(activity) }?.let {
            stack.search(activity) == 0
        } ?: false
    }
}