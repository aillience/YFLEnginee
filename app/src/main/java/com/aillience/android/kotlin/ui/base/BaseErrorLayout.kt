package com.aillience.android.kotlin.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.aillience.android.kotlin.R
import java.util.*

/**
 *===============================
 * 千万不要出现 B U G ，出现就 G G
 *    THE BEST CODE IS NO CODE
 *===============================
 * @author:yfl ;
 * @date: 2019-12-12
 * @description: 就是一个普通类
 * 一个自定义的RelativeLayout，主要用于一些布局上的显示问题
 * @lastUpdateTime 2019-12-12
 * #更新内容
 *===============================
 **/
class BaseErrorLayout(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {


    /**
     * 这是记录当前界面创建了的错误布局
     */
    private val errorViewStack: Stack<View> ?= Stack()
    private var currentView:View? = null
    private var listener: IBaseListener? = null

    enum class EnumType{
        ERROR_DEFAULT,
        ERROR_NETWORK
    }

    init {

    }

    /**
     * 显示指定布局
     */
    fun showAssignView(type: EnumType){
        val view = getView(type)
        addView(view)
        if(currentView != null){
            removeView(currentView)
        }
        currentView = view
    }

    /**
     * 移除所有指定控件
     */
    fun removeAllAssignView() {
        for (i in 0 until errorViewStack!!.size) {
            val view: View? = errorViewStack[i]
            if(view == currentView){
                this.removeView(view)
                currentView = null
                break
            }
        }
    }

    /**
     * 根据类型构建布局
     */
    @SuppressLint("InflateParams")
    private fun getView(type: EnumType): View {
        val view:View
        when(type){
            EnumType.ERROR_DEFAULT -> {
                //默认,不将此布局加入根布局addView
                val resId = R.layout.dialog_error_default
                isCreate = true
                view = getViewFrom(resId)
                if(isCreate){
                    initDefaultView(view)
                }
            }
            EnumType.ERROR_NETWORK ->{
                //网络错误
                val resId = R.layout.dialog_error_network
                isCreate = true
                view = getViewFrom(resId)
                if(isCreate){
                    initNetworkView(view)
                }
            }
        }
        return view
    }

    /**
     * 判断是否布局已经创造
     */
    private var isCreate = true
    private fun getViewFrom(resId: Int) : View {
        for(view in errorViewStack!!){
            if(view.id == resId){
                isCreate = false
                return view!!
            }
        }
        val view = LayoutInflater.from(context).inflate(resId, null)
        //设置当前控件大小
        view.layoutParams = this.layoutParams
        errorViewStack.add(view)
        return view
    }

    /**
     * 点击接口
     */
    interface IBaseListener{
        fun onClick(view:View,int: Int)
    }

    /**
     * 设置接口
     */
    fun setListener(listener: IBaseListener){
        this.listener = listener
    }

    /**
     * 默认布局
     */
    private fun initDefaultView(view: View){
        val text: TextView = view.findViewById(R.id.tv_title)
        text.text = "有点小问题哦"
        text.setOnClickListener {
            if(listener != null){
                listener!!.onClick(view, it.id)
            }
        }
    }

    /**
     * 网络异常布局
     */
    private fun initNetworkView(view: View){
        val text: TextView = view.findViewById(R.id.tv_title)
        text.text = "网络异常,请检查网络"
        text.setOnClickListener {
            if(listener != null){
                listener!!.onClick(view, it.id)
            }
        }
    }
}
