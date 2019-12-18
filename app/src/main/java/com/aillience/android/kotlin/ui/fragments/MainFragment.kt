package com.aillience.android.kotlin.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.aillience.android.kotlin.R
import com.aillience.android.kotlin.ui.base.BaseErrorLayout
import com.aillience.android.kotlin.ui.base.BaseFragment
import kotterknife.bindView

/**
 *===============================
 * 千万不要出现 B U G ，出现就 G G
 *    THE BEST CODE IS NO CODE
 *===============================
 * @author:yfl
 * @date: 2019-12-10
 * @description: 就是一个普通类
 *
 * @lastUpdateTime 2019-12-10
 * #更新内容
 *===============================
 **/
class MainFragment: BaseFragment() {

    private val relayoutMain: BaseErrorLayout by bindView(R.id.relayout_main)
    private val tvTitle: TextView by bindView(R.id.tv_title)

    companion object {
        fun newInstance(typeId: String): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle(2)
            bundle.putString("name", typeId)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * 布局文件ID
     */
    override fun getLayoutResID(): Int {
        return R.layout.fragment_main
    }

    override fun initViews() {
        super.initViews()
        if(arguments != null){
            this.tvTitle.text = arguments!!.getString("name")
            tvTitle.setOnClickListener {
                relayoutMain.showAssignView(BaseErrorLayout.EnumType.ERROR_NETWORK)
            }
            relayoutMain.setListener(listener)
        }
    }

    override fun initErrorView() {

    }

    private var listener =  object : BaseErrorLayout.IBaseListener {
        override fun onClick(view: View, int: Int) {
            relayoutMain.removeView(view)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        relayoutMain.removeAllAssignView()
    }
}
