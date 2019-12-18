package com.aillience.android.kotlin.ui.activities

import android.view.View
import android.widget.TextView
import com.aillience.android.kotlin.R
import com.aillience.android.kotlin.ui.base.BaseActivity
import com.aillience.android.kotlin.ui.base.BaseErrorLayout
import kotterknife.bindView

/**
 *===============================
 * 千万不要出现 B U G ，出现就 G G
 *    THE BEST CODE IS NO CODE
 *===============================
 * @author:yfl
 * @date: 2019-12-12
 * @description: 就是一个普通类
 *
 * @lastUpdateTime 2019-12-12
 * #更新内容
 *===============================
 **/
class TestActivity : BaseActivity() {

    private val tvTitle: TextView? by bindView(R.id.tv_title)
    private val frameLayout: BaseErrorLayout by bindView(R.id.frameLayout)
    /**
     * 布局文件ID
     */
    override fun getLayoutResID(): Int {
        return R.layout.activity_test
    }

    override fun initViews() {
        super.initViews()
        tvTitle!!.setOnClickListener {
            frameLayout.showAssignView(BaseErrorLayout.EnumType.ERROR_NETWORK)
        }
        frameLayout.setListener(listener)
    }

    private var listener =  object : BaseErrorLayout.IBaseListener {
        override fun onClick(view: View, int: Int) {
            frameLayout.removeView(view)
        }
    }
}