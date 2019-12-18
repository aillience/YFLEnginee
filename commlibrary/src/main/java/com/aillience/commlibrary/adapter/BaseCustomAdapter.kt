package com.aillience.commlibrary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView

/**
 * @author yfl
 * Happy every day.
 * Created by yfl on 2017/9/14 0014
 * explain: adapter自定义抽象类,listView用的
 */
abstract class BaseCustomAdapter<T> : BaseAdapter() {

    /**
     * 获取Context
     */
    protected abstract val context: Context
    /**
     * 放置数据
     */
    protected abstract val data: List<T>?
    /**
     * 放置布局id
     */
    protected abstract val layoutId: Int

    private var viewClick: ViewClickListener<T>? = null
    /**
     * 数据布局显示
     */
    protected abstract fun convert(holder: BaseViewHolder, item: T?, position: Int)

    interface ViewClickListener<T> {
        fun onItemClick(view: View, item: T?, position: Int)
        fun onItemLongClick(view: View, item: T?, position: Int)
    }

    /**
     * 点击事件
     */
    fun setViewClick(iViewClick: ViewClickListener<T>) {
        this.viewClick = iViewClick
    }

    override fun getCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    override fun getItem(position: Int): T? {
        return if (data == null) null else data!![position]
    }

    override fun getItemId(position: Int): Long {
        return (if (data == null) 0 else position).toLong()
    }

    override fun getView(position: Int, convert: View?, parent: ViewGroup): View {
        var convertView = convert
        val viewHolder: BaseViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                layoutId, parent,
                false
            )
            viewHolder = BaseViewHolder(convertView!!)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as BaseViewHolder
        }
        convert(viewHolder, getItem(position), position)
        if (viewClick != null) {
            val finalConvertView = convertView
            convertView.setOnClickListener { viewClick!!.onItemClick(finalConvertView, getItem(position), position) }
            convertView.setOnLongClickListener {
                viewClick!!.onItemLongClick(finalConvertView, getItem(position), position)
                false
            }
        }
        return convertView
    }

    /**
     * 单条数据更新
     * @param position 要更新的位置
     */
    private fun updateSingle(listView: ListView, position: Int) {
        /*第一个可见的位置**/
        val firstVisiblePosition = listView.firstVisiblePosition
        /*最后一个可见的位置**/
        val lastVisiblePosition = listView.lastVisiblePosition
        /*在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position in firstVisiblePosition..lastVisiblePosition) {
            /*获取指定位置view对象**/
            val view = listView.getChildAt(position - firstVisiblePosition)
            getView(position, view, listView)
        }
    }

}
