package com.aillience.android.kotlin.ui.base

import android.text.Spanned

/**
 *===============================
 * 千万不要出现 B U G ，出现就 G G
 *    THE BEST CODE IS NO CODE
 *===============================
 * @author:yfl
 * @date: 2019-12-10
 * @description: 就是一个普通类
 * 在视图界面上的通用接口，在activity和fragment上使用的
 * @lastUpdateTime 2019-12-10
 * #更新内容
 *===============================
 **/
interface IBaseInterface{
    /**
     * 隐藏软键盘
     */
    fun hideInput()

    /**
     * 普通跳转其它界面
     */
    fun startOneActivity(activity: Class<*>)

    /**
     * getResources().getColor() 过时，用此代替
     */
    fun getNewColor(colorId: Int): Int

    /**
     *fromHtml 过时问题，重写,用于界面中调整文字
     */
    fun getFromHtml(htmlString: String): Spanned
}