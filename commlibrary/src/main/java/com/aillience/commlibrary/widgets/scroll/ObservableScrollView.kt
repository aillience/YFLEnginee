package com.aillience.commlibrary.widgets.scroll

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * 监听ScrollView的滑动数据
 * 用于滑动顶部固定等操作
 */
class ObservableScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {


    private var mOnObservableScrollViewScrollChanged: OnObservableScrollViewScrollChanged? = null

    fun setOnObservableScrollViewScrollChanged(mOnObservableScrollViewScrollChanged: OnObservableScrollViewScrollChanged) {
        this.mOnObservableScrollViewScrollChanged = mOnObservableScrollViewScrollChanged
    }

    interface OnObservableScrollViewScrollChanged {
        fun onObservableScrollViewScrollChanged(l: Int, t: Int, ol: Int, ot: Int)
    }

    /**
     * @param l Current horizontal scroll origin. 当前滑动的x轴距离
     * @param t Current vertical scroll origin. 当前滑动的y轴距离
     * @param ol Previous horizontal scroll origin. 上一次滑动的x轴距离
     * @param ot Previous vertical scroll origin. 上一次滑动的y轴距离
     */
    override fun onScrollChanged(l: Int, t: Int, ol: Int, ot: Int) {
        super.onScrollChanged(l, t, ol, ot)
        if (mOnObservableScrollViewScrollChanged != null) {
            mOnObservableScrollViewScrollChanged!!.onObservableScrollViewScrollChanged(l, t, ol, ot)
        }
    }
}
