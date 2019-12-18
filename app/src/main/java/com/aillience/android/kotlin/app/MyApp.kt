package com.aillience.android.kotlin.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle

/**
 *===============================
 * 千万不要出现 B U G ，出现就 G G
 *    THE BEST CODE IS NO CODE
 *===============================
 * @author:yfl
 * @date: 2019-12-11
 * @description: 就是一个普通类
 *
 * @lastUpdateTime 2019-12-11
 * #更新内容
 *===============================
 **/
class MyApp :Application() {

    /**
     * 全局静态
     */
    companion object {
        var appContext: Context? = null
        var TAG = "bbl-kotlin"
    }


    /**
     * 应用是否在后台
     */
    private var isBackGround = false

    override fun onCreate() {
        super.onCreate()
        synchronized(MyApp::class.java) {
            if (appContext == null) {
                appContext = this
            }
        }
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    private val activityLifecycleCallbacks = object : ActivityLifecycleCallbacks{
        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
            if (isBackGround) {
                //回到了前台
                isBackGround = false
            }
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        }

    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            //APP遁入了后台
            isBackGround = true
        }
    }
}