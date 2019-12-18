package com.aillience.commlibrary.widgets

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.view.Gravity
import android.widget.Toast
import android.widget.Toast.makeText

/**
 *===============================
 * 千万不要出现 B U G ，出现就 G G
 *    THE BEST CODE IS NO CODE
 *===============================
 * @author:yfl
 * @date: 2019-04-01
 * @description:PublicToast
 *因为功能需要，根据网上资料整理加工，定义一个可控制显示时间的Toast
 * @lastUpdateTime 2019-04-02
 * #更新内容
 * 构造器写法有很大改变
 * class PublicToast constructor(context: Context)
 *===============================
 **/

/**调用Toast */
class PublicToast constructor(context: Context) {

    //自定义时间的显示是否已经结束，默认true 已经结束
    private var mCanceled = true
    private var mHandler: Handler? = null
    private var mContext: Context? = context
    private var mToast: Toast? = null

    init {
        mHandler = Handler()
        create()
    }

    private fun create(){
        if(mToast == null){
            mToast = makeText(mContext, "", Toast.LENGTH_SHORT)
            mToast!!.setGravity(Gravity.BOTTOM, 0, 0)
            mToast!!.show()
            mToast!!.cancel()
        }
    }

    /**
     * 从资源文件中找显示内容,
     */
    private fun show(resId: Int, duration: Int) {
        show(mContext!!.getString(resId),duration)
    }

    private fun show(text: String, duration: Int) {
        mToast!!.setText(text)
        when (duration){
            longTime ->{
                mToast!!.duration = duration
                mToast!!.show()
            }

            shortTime ->{
                mToast!!.duration = duration
                mToast!!.show()
            }

            foreverTime ->{
                if(mCanceled){
                    mToast!!.duration = Toast.LENGTH_LONG
                    mCanceled = false
                    showUntilCancel()
                }
            }
        }
    }

    /**
     * @param text
     * 要显示的内容
     * @param duration
     * 显示的时间长 根据LENGTH_MAX进行判断 如果不匹配，进行系统显示 如果匹配，永久显示，直到调用hide()
     * 注意：永久显示showMax时，若不取消永久显示hide，直接调用将出现Toast闪屏
     */
    fun show(text: String, duration: Long) {
        mToast!!.setText(text)
        showAnyTime(text, duration)
    }

    /**
     * 默认3秒显示
     * String m_ToastStr = "<font color='#EE0000'>"+m_Str+"</font>"
     * Html.fromHtml(m_ToastStr)
     */
    fun show(text: String) {
        mToast!!.setText(text)
        showAnyTime(text, 3000)
    }

    /**理论上的永久显示,在toast显示时间结束时再次开启显示
     * 但不应该这样子做，故私有不允许外部引用
     * */
    private fun showForever(text: String) {
        mToast!!.setText(text)
        if (mCanceled) {
            //已经结束，开启长时间显示
            mCanceled = false
            mToast!!.duration = Toast.LENGTH_LONG
            mToast!!.setGravity(Gravity.BOTTOM, 0, -1)
            if(mToast!!.view != null){
                mToast!!.view.setBackgroundColor(Color.TRANSPARENT)
            }
            showUntilCancel()
        }
    }

    /**
     * 隐藏Toast
     */
    private fun hide() {
        mToast!!.setGravity(Gravity.BOTTOM, 0, 0)
        mToast!!.cancel()
        //结束长时间显示
        mCanceled = true
    }

    /**
     * 显示3秒完成后再次显示
     */
    private fun showUntilCancel() {
        if (mCanceled){
            //已经结束，不再执行后续
            return
        }
        mToast!!.show()
        mHandler!!.postDelayed({
            showUntilCancel() }, 3000)
    }

    /**
     * @param text
     * @param t
     * 自定义显示时间
     */
    private fun showAnyTime(text: String, t: Long) {
        showForever(text)
        mHandler!!.postDelayed({ hide() }, t)
    }

    companion object {
        //短时间显示
        const val shortTime = Toast.LENGTH_SHORT
        //长时间显示
        const val longTime = Toast.LENGTH_LONG
        //一直显示
        const val foreverTime = 2
    }

}