package com.aillience.android.kotlin.app

import android.app.Activity

import java.util.Stack

/**
 *===============================
 * 千万不要出现 B U G ，出现就 G G
 *    THE BEST CODE IS NO CODE
 *===============================
 * @author:yfl
 * @date: 2019-04-01
 * @description:Activity管理类
 * 私有构造器，单例对象，用于管理应用的界面
 * @lastUpdateTime 2019-04-02
 * #更新内容
 *===============================
 **/

class MyActivityManager private constructor()//构造器私有，通过newInstance获取
{
    private var activityStack: Stack<Activity>? = null

    /**
     * 判断是否有某一个activity
     */
    fun hasOneActivity(activityName: String): Boolean {
        for (activity in activityStack!!) {
            if (activity.javaClass.simpleName == activityName) {
                return true
            }
        }
        return false
    }

    /**
     * 添加一个activity
     */
    fun addOneActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        if (!activityStack!!.contains(activity)) {
            activityStack!!.add(activity)
        }
    }

    /**
     * 移除指定activity
     */
    fun removeOneActivity(activity: Activity?) {
        try {
            if (activityStack != null && activityStack!!.size > 0) {
                if (activity != null) {
                    activityStack!!.remove(activity)
                    activity.finish()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 通过名称移除指定activity
     */
    fun removeOneActivity(activityName: String) {
        if (activityStack != null && activityStack!!.size > 0) {
            for (i in activityStack!!.indices.reversed()) {
                val activity = activityStack!![i]
                if (activity.javaClass.simpleName == activityName) {
                    activityStack!!.remove(activity)
                    activity.finish()
                }
            }
        }
    }

    /**
     * 结束所有显示界面
     */
    private fun finishAllActivity() {
        for (activity in activityStack!!) {
            activity?.finish()
        }
        activityStack!!.clear()
    }

    /**
     * 退出应用程序
     */
    fun exitApp() {
        try {
            finishAllActivity()
            System.exit(0)
            android.os.Process.killProcess(android.os.Process.myPid())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 单例
     */
    companion object {
        private var activityManager: MyActivityManager? = null
        fun newInstance(): MyActivityManager {
            if (activityManager == null) {
                synchronized(MyActivityManager::class.java) {
                    activityManager =
                        MyActivityManager()
                }
            }
            return activityManager!!
        }
    }
}