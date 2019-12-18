package com.aillience.commlibrary.widgets.edit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.AppCompatEditText
import android.text.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import com.aillience.commlibrary.R

class ClearEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyle), OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private var mClearDrawable: Drawable? = null
    /**
     * 控件是否有焦点
     */
    private var hasFoucs: Boolean = false

    init {
        init()
    }


    private fun init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = compoundDrawables[2]
        if (mClearDrawable == null) {
            //          throw new NullPointerException("You can add drawableRight attribute in XML");
            //            mClearDrawable = getResources().getDrawable(R.drawable.icon_my_edit_del);
            mClearDrawable = ResourcesCompat.getDrawable(resources, R.drawable.icon_my_edit_del, null)
        }
        mClearDrawable!!.setBounds(0, 0, mClearDrawable!!.intrinsicWidth, mClearDrawable!!.intrinsicHeight)
        //开启硬件加速（强烈推荐）
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        //默认设置隐藏图标
        setClearIconVisible(false)
        //设置焦点改变的监听
        onFocusChangeListener = this
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this)
        //如果是小数，限制在两位
        if (inputType == InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL) {
            filters = arrayOf<InputFilter>(PointLengthFilter())
        }
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {

                val touchable = event.x > width - totalPaddingRight && event.x < width - paddingRight

                if (touchable) {
                    this.setText("")
                }
            }
        }

        return super.onTouchEvent(event)
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        this.hasFoucs = hasFocus
        if (hasFocus) {
            setClearIconVisible(text!!.isNotEmpty())
        } else {
            setClearIconVisible(false)
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    private fun setClearIconVisible(visible: Boolean) {
        //add by yfl on 20 图标之间的间距
        val pad = if (visible) 10 else 0
        compoundDrawablePadding = pad

        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1], right, compoundDrawables[3]
        )
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    override fun onTextChanged(
        s: CharSequence, start: Int, count: Int,
        after: Int
    ) {
        if (hasFoucs) {
            setClearIconVisible(s.isNotEmpty())
            if (text != null && containsEmoji(text!!.toString())) {
                //                MyToastUtil.showString(getContext(),"表情等字符可能导致操作失败,建议重新输入",false);
            }
        }
    }

    override fun beforeTextChanged(
        s: CharSequence, start: Int, count: Int,
        after: Int
    ) {

    }

    override fun afterTextChanged(s: Editable) {

    }

    /**
     * 设置晃动动画
     */
    fun setShakeAnimation() {
        this.animation = shakeAnimation(5)
    }

    inner class PointLengthFilter : InputFilter {

        override fun filter(
            source: CharSequence, start: Int, end: Int,
            dest: Spanned, dstart: Int, dend: Int
        ): CharSequence? {
            // 删除等特殊字符，直接返回
            if ("" == source.toString()) {
                return null
            }
            val dValue = dest.toString()
            val splitArray = dValue.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (splitArray.size > 1) {
                val dotValue = splitArray[1]
                val diff = dotValue.length + 1 - DECIMAL_DIGITS
                if (diff > 0) {
                    return source.subSequence(start, end - diff)
                }
            }
            return null
        }


    }
    //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
    companion object {
        /** 输入框小数的位数  示例保留一位小数 */
        private const val DECIMAL_DIGITS = 2
        /**
         * 检测是否有emoji表情
         *
         * @param source
         * @return
         */
        fun containsEmoji(source: String?): Boolean {
            if (source == null) {
                return false
            }
            val len = source.length
            for (i in 0 until len) {
                val codePoint = source[i]
                if (!isEmojiCharacter(codePoint)) {
                    //如果不能匹配,则该字符是Emoji表情
                    return true
                }
            }
            return false
        }

        /**
         * 判断是否是Emoji
         *
         * @param codePoint 比较的单个字符
         * @return
         */
        private fun isEmojiCharacter(codePoint: Char): Boolean {
            return codePoint.toInt() == 0x0 ||
                    codePoint.toInt() == 0x9 ||
                    codePoint.toInt() == 0xA ||
                    codePoint.toInt() == 0xD ||
                    codePoint.toInt() in 0x20..0xD7FF ||
                    codePoint.toInt() in 0xE000..0xFFFD
        }

        /**
         * 晃动动画
         * @param counts 1秒钟晃动多少下
         * @return
         */
        fun shakeAnimation(counts: Int): Animation {
            val translateAnimation = TranslateAnimation(0f, 10f, 0f, 0f)
            translateAnimation.interpolator = CycleInterpolator(counts.toFloat())
            translateAnimation.duration = 1000
            return translateAnimation
        }
    }
}
