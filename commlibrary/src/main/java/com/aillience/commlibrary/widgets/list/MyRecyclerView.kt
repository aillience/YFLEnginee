package com.aillience.commlibrary.widgets.list


import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View


/**
 * Happy every day.
 * Created by yfl on 2017/9/18 0018
 * explain: RecyclerView基类
 * 添加空列表
 */
@SuppressLint("Recycle")
class MyRecyclerView : RecyclerView {

    private var layoutManager: LinearLayoutManager? = null
    private var mContext: Context? = null

    private var mLayoutManager: LinearLayoutManager? = null
    private var mGridLayoutManager: GridLayoutManager? = null

    /**
     * 当数据为空时展示的View
     */
    private var mEmptyView: View? = null

    private var onScrollListener: OnScrollListener? = null

    /**
     * 创建一个观察者
     * 为什么要在onChanged里面写？
     * 因为每次notifyDataChanged的时候，系统都会调用这个观察者的onChange函数
     * 我们大可以在这个观察者这里判断我们的逻辑，就是显示隐藏
     */
    private val emptyObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            val adapter = adapter
            //这种写发跟之前我们之前看到的ListView的是一样的，判断数据为空否，在进行显示或者隐藏
            if (adapter != null && mEmptyView != null) {
                if (adapter.itemCount == 0) {
                    mEmptyView!!.visibility = View.VISIBLE
                    this@MyRecyclerView.visibility = View.GONE
                } else {
                    mEmptyView!!.visibility = View.GONE
                    this@MyRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        initView(context)
    }

    private fun initView(context: Context) {
        mContext = context
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        this.setHasFixedSize(true)
        this.isNestedScrollingEnabled = false
        //解决数据加载完成后, 没有停留在顶部的问题
        this.isFocusable = false
        //假如在自动滑动时，不加载图片
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                /*
                 * RecyclerView.SCROLL_STATE_IDLE //空闲状态
                 * RecyclerView.SCROLL_STATE_SETTLING //自动滚动开始
                 * RecyclerView.SCROLL_STATE_DRAGGING //正在被外部拖拽,一般为用户正在用手指滚动
                 */
                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                } else {
                }
            }
        })
    }


    /**
     * LinearLayoutManager布局
     * @param vertical //是否垂直
     */
    fun setLinearLayoutManager(vertical: Boolean) {
        if (mLayoutManager == null) {
            mLayoutManager = object : LinearLayoutManager(mContext) {
                override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
                    //预加载10
                    return 10
                }
            }

            if (vertical) {
                mLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
            } else {
                mLayoutManager!!.orientation = LinearLayoutManager.HORIZONTAL
            }
        }
        layoutManager = mLayoutManager
        this.setLayoutManager(mLayoutManager)
    }

    /**
     * GridLayoutManager布局
     * @param spanCount //行数
     * @param vertical  //是否垂直
     */
    fun setGridLayoutManager(spanCount: Int, vertical: Boolean) {
        if (mGridLayoutManager == null) {
            mGridLayoutManager = object : GridLayoutManager(mContext, spanCount) {
                override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
                    //预加载10
                    return 10
                }
            }
            if (vertical) {
                mGridLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
            } else {
                mGridLayoutManager!!.orientation = LinearLayoutManager.HORIZONTAL
            }
        }
        layoutManager = mGridLayoutManager
        this.setLayoutManager(mGridLayoutManager)
    }

    override fun getLayoutManager(): LinearLayoutManager? {
        return layoutManager
    }

    /**
     * 设置LayoutManager
     * @param isDefault  默认为sLinearLayoutManager
     * @param vertical   是否垂直
     * @param spanCount  行数
     */
    fun setLayoutManager(isDefault: Boolean, vertical: Boolean, spanCount: Int) {
        if (isDefault) {
            setLinearLayoutManager(vertical)
        } else {
            setGridLayoutManager(spanCount, vertical)
        }
    }

    //设置Orientation布局，默认为垂直
    fun setOrientation(vertical: Boolean) {
        if (layoutManager == null) {
            return
        }
        if (vertical) {
            layoutManager!!.orientation = OrientationHelper.VERTICAL
            invalidate()
        } else {
            layoutManager!!.orientation = OrientationHelper.HORIZONTAL
            invalidate()
        }
    }

    private fun dp2px(context: Context, dipValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale).toInt().toFloat()
    }

    /**
     * 获取当前可见的屏幕上第一个位置
     * @return
     */
    fun findFirstVisibleItemPositions(): Int {
        return if (layoutManager != null) {
            layoutManager!!.findFirstVisibleItemPosition()
        } else 0
    }

    /**
     * 当前可见的最后一个位置
     */
    fun findLastVisibleItemPositions(): Int {
        return if (layoutManager != null) {
            layoutManager!!.findLastVisibleItemPosition()
        } else 0
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (onScrollListener != null) {
            onScrollListener!!.onScroll(l, t, oldl, oldt)
        }
    }

    /**
     * 设置滚动接口
     * @param onScrollListener
     */
    fun setScrolListener(onScrollListener: OnScrollListener) {
        this.onScrollListener = onScrollListener
    }

    interface OnScrollListener {
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         * @param scrollX X距离
         * @param scrollY Y距离
         * @param oldl 旧距离
         * @param oldt 旧距离
         */
        fun onScroll(scrollX: Int, scrollY: Int, oldl: Int, oldt: Int)
    }

    /**
     * 依赖注入
     * @param emptyView 展示的空view
     */
    fun setEmptyView(emptyView: View) {
        mEmptyView = emptyView
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(emptyObserver)
        //当setAdapter的时候也调一次
        emptyObserver.onChanged()
    }
}
