package com.aillience.android.kotlin.ui.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.Spanned
import android.view.inputmethod.InputMethodManager

/**
 *===============================
 * 千万不要出现 B U G ，出现就 G G
 *    THE BEST CODE IS NO CODE
 *===============================
 * @author:yfl
 * @date: 2019-12-10
 * @description: 就是一个普通类
 * 接口实现
 * @lastUpdateTime 2019-12-10
 * #更新内容
 *===============================
 **/
class BaseInterfaceImpl(activity: Activity) : IBaseInterface {

    /**
     * 设置当前界面上下文
     * 实现接口必须要实现的
     */
    private var mActivity: Activity? = activity

    /**
     * 隐藏软键盘
     */
    override fun hideInput() {
        val imm = mActivity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = mActivity!!.window.peekDecorView()
        if (null != v) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    /**
     * 普通跳转其它界面
     */
    override fun startOneActivity(activity: Class<*>) {
        val intent = Intent(mActivity, activity)
        mActivity!!.startActivity(intent)
    }

    /**
     * getResources().getColor() 过时，用此代替
     */
    override fun getNewColor(colorId: Int): Int {
        return ContextCompat.getColor(mActivity!!, colorId)
    }

    /**
     *fromHtml 过时问题，重写,用于界面中调整文字
     */
    @Suppress("DEPRECATION")
    override fun getFromHtml(htmlString: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(htmlString)
        }
    }
}