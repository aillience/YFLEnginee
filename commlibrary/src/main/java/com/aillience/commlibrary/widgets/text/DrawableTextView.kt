package com.aillience.commlibrary.widgets.text

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.aillience.commlibrary.R

/**
 * ===============================
 * 千万不要出现 B U G ，出现就 G G
 * THE BEST CODE IS NO CODE
 * ===============================
 *
 * @author:yfl
 * @date: 2019-10-12
 * @description: 网络资源修改
 * @lastUpdateTime 2019-10-12
 * #更新内容 设置图标的TextView
 * 可以显示为
 *  · 文字换行后，
 *    左边的图片还是在第一行的中间
 * ===============================
 */
class DrawableTextView @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attrs, defStyleAttr) {
    // 控件左、上、右、下图标的宽和高
    private var drawableLeftWidth: Int = 0
    private var drawableTopWidth: Int = 0
    private var drawableRightWidth: Int = 0
    private var drawableBottomWidth: Int = 0
    private var drawableLeftHeight: Int = 0
    private var drawableTopHeight: Int = 0
    private var drawableRightHeight: Int = 0
    private var drawableBottomHeight: Int = 0
    // 默认显示方式
    private var alignCenter = true
    private var mWidth: Int = 0

    init {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView)
        drawableLeftWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_drawableLeftWidth, 0)
        drawableTopWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_drawableTopWidth, 0)
        drawableRightWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_drawableRightWidth, 0)
        drawableBottomWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_drawableBottomWidth, 0)
        drawableLeftHeight = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_drawableLeftHeight, 0)
        drawableTopHeight = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_drawableTopHeight, 0)
        drawableRightHeight = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_drawableRightHeight, 0)
        drawableBottomHeight = typedArray.getDimensionPixelSize(R.styleable.DrawableTextView_drawableBottomHeight, 0)
        alignCenter = typedArray.getBoolean(R.styleable.DrawableTextView_alignCenter, true)
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        val drawables = compoundDrawables
        val drawableLeft = drawables[0]
        val drawableTop = drawables[1]
        val drawableRight = drawables[2]
        val drawableBottom = drawables[3]
        if (drawableLeft != null) {
            setDrawable(drawableLeft, 0, drawableLeftWidth, drawableLeftHeight)
        }
        if (drawableTop != null) {
            setDrawable(drawableTop, 1, drawableTopWidth, drawableTopHeight)
        }
        if (drawableRight != null) {
            setDrawable(drawableRight, 2, drawableRightWidth, drawableRightHeight)
        }
        if (drawableBottom != null) {
            setDrawable(drawableBottom, 3, drawableBottomWidth, drawableBottomHeight)
        }
        this.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom)
    }

    /**
     * 设置图标大小及其偏移量
     */
    private fun setDrawable(drawable: Drawable, tag: Int, drawableWidth: Int, drawableHeight: Int) {
        val width = if (drawableWidth == 0) drawable.intrinsicWidth else drawableWidth
        val height = if (drawableHeight == 0) drawable.intrinsicHeight else drawableHeight
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0
        when (tag) {
            0, 2 -> {
                left = 0
                top = if (alignCenter) 0 else -lineCount * lineHeight / 2 + lineHeight / 2
                right = width
                bottom = top + height
            }
            1, 3 -> {
                left = if (alignCenter) 0 else -mWidth / 2 + width / 2
                top = 0
                right = left + width
                bottom = top + height
            }
            else -> {
            }
        }
        drawable.setBounds(left, top, right, bottom)
    }
}
