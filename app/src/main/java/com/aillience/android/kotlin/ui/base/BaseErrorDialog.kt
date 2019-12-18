package com.aillience.android.kotlin.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.LinearLayout
import android.widget.TextView
import android.view.*
import android.view.MotionEvent
import com.aillience.android.kotlin.R


/**
 *===============================
 * 千万不要出现 B U G ，出现就 G G
 *    THE BEST CODE IS NO CODE
 *===============================
 * @author:yfl
 * @date: 2019-12-11
 * @description: 就是一个普通类
 *一个通用的错误显示界面，静态布局，比如网络异常，数据异常等
 * 通过type传入值，获取界面布局 --
 * 这个没完成最好还是不要用，出问题不好,还是用 BaseErrorLayout 吧
 * 保留一个模板在这里
 * @lastUpdateTime 2019-12-11
 * #更新内容
 *===============================
 **/
class BaseErrorDialog private constructor(context: Activity) : Dialog(context, R.style.BaseDialog) {

    enum class EnumType{
        ERROR_DEFAULT,
        ERROR_NETWORK
    }

    companion object {
        var STATUS_DEFAULT = -101
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //mWindow.shouldCloseOnTouch(mContext, event)返回值就是上面的设置值，true会进入
        //cancel方法。
        if (event.action == MotionEvent.ACTION_OUTSIDE) {
            //如果点击位置在当前View外部则销毁当前视图，这里来判断位置
        }
        return true
    }

    class Builder(context: Activity) {
        private var mContext: Activity = context
        private var root:ViewGroup ?= null
        private var type: EnumType? =
            EnumType.ERROR_DEFAULT
        private var listener: DialogInterface.OnClickListener ?= null
        private var dialog: BaseErrorDialog?= null

        /**
         * 设置根
         */
        fun setRootView(view: ViewGroup): Builder {
            this.root = view
            return this
        }
        /**
         * 设置类型
         */
        fun setType(type: EnumType): Builder {
            this.type = type
            return this
        }

        /**
         * 设置监听
         */
        fun setListener(listener: DialogInterface.OnClickListener): Builder {
            this.listener = listener
            return this
        }

        /**
         * 创建显示布局
         */
        fun create(): BaseErrorDialog {
            dialog = BaseErrorDialog(mContext)
            val view = getView(this.type!!)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setContentView(view,
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            )
            //清空原来的
            dialog!!.window!!.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
            //设置 这种模式当触发outside touch时响应
            dialog!!.window!!.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH)
            //强制性的，点击外部不消失
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.setCancelable(false)
            //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //一定要在setContentView之后调用，否则无效
            dialog!!.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            return dialog!!
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
                    view = LayoutInflater.from(mContext).inflate(R.layout.dialog_error_default, null)
                    initDefaultView(view)
                }
                EnumType.ERROR_NETWORK ->{
                    //网络错误
                    view = LayoutInflater.from(mContext).inflate(R.layout.dialog_error_network, null)
                    initNetworkView(view)
                }
            }
            return view
        }

        /**
         * 默认布局
         */
        private fun initDefaultView(view: View){
            val text:TextView = view.findViewById(R.id.tv_title)
            text.text = "这是一条默认异常"
            text.setOnClickListener {
                if(listener != null){
                    listener!!.onClick(dialog,
                        STATUS_DEFAULT
                    )
                }
            }
        }

        /**
         * 网络异常布局
         */
        private fun initNetworkView(view: View){
            val text:TextView = view.findViewById(R.id.tv_title)
            text.text = "网络异常,请检查网络"
            text.setOnClickListener {
                if(listener != null){
                    listener!!.onClick(dialog,
                        STATUS_DEFAULT
                    )
                }
            }
        }

    }

}