package com.aillience.commlibrary.adapter

import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {

    protected abstract val context: Context
    protected abstract val data: MutableList<T>?
    protected abstract val layoutId: Int
    private var clickEvent: ViewClick<T>? = null
    protected abstract fun bindViewHolder(holder: BaseViewHolder, item: T?, position: Int)

    interface ViewClick<T> {
        fun onItemClickEvent(view: View, item: T?, position: Int)
        fun onItemLongClickEvent(view: View, item: T?, position: Int)
    }

    fun setViewClick(action: ViewClick<T>) {
        this.clickEvent = action
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(context).inflate(layoutId, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        //绑定BaseViewHolder的数据显示，监听等
        //        BaseViewHolder baseViewHolder = null;
        bindViewHolder(holder, getItem(position), position)
        if (clickEvent != null) {
            val finalConvertView = holder.convertView
            finalConvertView.setOnClickListener { v -> clickEvent!!.onItemClickEvent(v, getItem(position), position) }
            finalConvertView.setOnLongClickListener { v ->
                clickEvent!!.onItemLongClickEvent(v, getItem(position), position)
                false
            }
        }
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return if (data != null) data!!.size else 0
    }

    private fun getItem(position: Int): T? {
        return if (data == null) null else data!![position]
    }

    /**
     * 批量插入数据
     */
    fun addAllData(items: List<T>) {
        data!!.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * 插入数据到指定位置
     */
    fun addData(position: Int, item: T) {
        data!!.add(position, item)
        notifyItemInserted(position)
    }

    /**
     * 删除指定数据
     */
    fun deleteData(position: Int) {
        data!!.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * getResources().getColor() 过时，用此代替
     */
    protected fun getNewColor(colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

    /**
     * fromHtml 过时问题
     */
    @Suppress("DEPRECATION")
    protected fun getFromHtml(htmlString: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(htmlString)
        }
    }

    /**
     * 取像素-- 14 *2
     */
    protected fun sp2px(size: Int): Float {
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        return size * scaledDensity
    }

}
