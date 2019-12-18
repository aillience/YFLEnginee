package com.aillience.commlibrary.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView


import java.util.ArrayList


class BaseViewHolder(val convertView: View) : RecyclerView.ViewHolder(convertView) {

    private val views: SparseArray<View> = SparseArray()
    private var mContext: Context? = null

    /**
     * 获取Context
     * 默认为mContext，没有取mConvertView.getContext()
     */
    private val context: Context get() {
            if (mContext == null) {
                mContext = convertView.context
            }
            return mContext!!
        }

    init {

    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    fun <V : View> getView(viewId: Int): V? {
        @Suppress("UNCHECKED_CAST")
        var view: V? = views.get(viewId) as V
        if (view == null) {
            view = convertView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view
    }

    /**
     * 为TextView设置字符串
     */
    fun setText(viewId: Int, text: String): TextView {
        val view = getView<TextView>(viewId)
        view!!.text = getString(text)
        return view
    }

    private fun getString(text: String?): String {
        return text ?: ""
    }

    /**
     * 设置字体颜色
     */
    fun setTextColor(viewId: Int, colorId: Int): TextView {
        val view = getView<TextView>(viewId)
        view!!.setTextColor(ContextCompat.getColor(context, colorId))
        return view
    }

    /**
     * 为ImageView设置图片
     */
    fun setImageResource(viewId: Int, drawableId: Int): ImageView {
        val view = getView<ImageView>(viewId)
        view!!.setImageResource(drawableId)
        return view
    }

    /**
     * 控件设置背景图片以及透明度
     */
    fun setBackgroundResource(viewId: Int, drawableId: Int): View {
        return setBackgroundResource(viewId, drawableId, 0)
    }

    /**
     * 控件设置背景图片以及透明度
     */
    private fun setBackgroundResource(viewId: Int, drawableId: Int, alpha: Int): View {
        val v = getView<View>(viewId)
        v!!.setBackgroundResource(drawableId)
        if (alpha != 0) {
            v.background.alpha = alpha
        }
        return v
    }

}
