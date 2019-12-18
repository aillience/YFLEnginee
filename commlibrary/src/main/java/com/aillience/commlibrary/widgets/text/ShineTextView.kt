package com.aillience.commlibrary.widgets.text

import android.content.Context
import android.graphics.*
import android.util.AttributeSet

/**
 * ===============================
 * 千万不要出现 B U G ，出现就 G G
 * ===============================
 * @author:yfl
 * @date: 2019-03-12
 * @description:就是一个普通类
 * 闪闪发光的 textview 网络资源
 * ===============================
 */
class ShineTextView(context: Context, attrs: AttributeSet) :
    android.support.v7.widget.AppCompatTextView(context, attrs) {
    private var mLinearGradient: LinearGradient? = null
    private var mGradientMatrix: Matrix? = null
    private var mPaint: Paint? = null
    private var mViewWidth = 0
    private var mTranslate = 0

    private val mAnimating = true

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mViewWidth == 0) {
            mViewWidth = measuredWidth
            if (mViewWidth > 0) {
                mPaint = paint
                // 创建LinearGradient对象
                // 起始点坐标（-mViewWidth, 0） 终点坐标（0，0）
                // 第一个,第二个参数表示渐变起点 可以设置起点终点在对角等任意位置
                // 第三个,第四个参数表示渐变终点
                // 第五个参数表示渐变颜色  new int[] { 0xFF0288DA, 0xFFFFFFFF, 0xFF0288DA },
                // 第六个参数可以为空,表示坐标,值为0-1,如果这是空的，颜色均匀分布，沿梯度线。
                // 第七个表示平铺方式
                // CLAMP重复最后一个颜色至最后
                // MIRROR重复着色的图像水平或垂直方向已镜像方式填充会有翻转效果
                // REPEAT重复着色的图像水平或垂直方向
                mLinearGradient = LinearGradient(
                    (-mViewWidth).toFloat(), 0f,
                    0f, 0f,
                    intArrayOf(0x00000000, -0x1, 0x00000000),
                    floatArrayOf(0f, 0.5f, 1f),
                    Shader.TileMode.CLAMP
                )
                mPaint!!.shader = mLinearGradient
                mGradientMatrix = Matrix()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mAnimating && mGradientMatrix != null) {
            mTranslate += mViewWidth / 10
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth
            }
            mGradientMatrix!!.setTranslate(mTranslate.toFloat(), 0f)
            mLinearGradient!!.setLocalMatrix(mGradientMatrix)
            postInvalidateDelayed(50)
        }
    }
}
