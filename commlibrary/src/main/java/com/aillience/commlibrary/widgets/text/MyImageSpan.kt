package com.aillience.commlibrary.widgets.text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ImageSpan

/**
 * ===============================
 * 千万不要出现 B U G ，出现就 G G
 * THE BEST CODE IS NO CODE
 * ===============================
 *
 * @author:yfl
 * @date: 2019-11-25
 * @description: 就是一个普通类
 * @lastUpdateTime 2019-11-25
 * #更新内容 用于居中的 ImageSpan
 * 简单用法
 * SpannableString spannableString = new SpannableString("  这里是 TextView 的内容");
 * MyImageSpan span = new MyImageSpan(getContext(),图片ID);
 * spannableString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
 * TextView.setText(spannableString);
 * ===============================
 */
class MyImageSpan
/**
 * Constructs an [ImageSpan] from a [Context]
 * @param context    context used to retrieve the drawable from resources
 * @param resourceId drawable resource id based on which the drawable is retrieved
 */
    (context: Context, resourceId: Int) : ImageSpan(context, resourceId) {

    override fun getSize(
        paint: Paint, text: CharSequence, start: Int, end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        val d = drawable
        val rect = d.bounds
        if (fm != null) {
            val fmPaint = paint.fontMetricsInt
            val fontHeight = fmPaint.bottom - fmPaint.top
            val drHeight = rect.bottom - rect.top

            val top = drHeight / 2 - fontHeight / 4
            val bottom = drHeight / 2 + fontHeight / 4

            fm.ascent = -bottom
            fm.top = -bottom
            fm.bottom = top
            fm.descent = top
        }
        return rect.right
    }

    override fun draw(
        canvas: Canvas, text: CharSequence, start: Int, end: Int,
        x: Float, top: Int, y: Int, bottom: Int, paint: Paint
    ) {
        val b = drawable
        canvas.save()
        val transY = (bottom - top - b.bounds.bottom) / 2 + top
        canvas.translate(x, transY.toFloat())
        b.draw(canvas)
        canvas.restore()
    }
}
