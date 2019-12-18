package com.aillience.android.kotlin.ui.activities

import android.support.v4.app.Fragment
import com.aillience.android.kotlin.R
import com.aillience.android.kotlin.ui.base.BaseActivity
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.aillience.android.kotlin.ui.fragments.MainFragment
import kotterknife.bindView
import java.util.ArrayList

/**
 * @author yfl
 * 这个是帮帮礼项目为原型，java 代码转 Kotlin 的项目，
 * 没有周期，做到哪里就是哪里，可能随时停的
 * 刚好在此过程中，梳理代码结构，进步思想
 */
class MainActivity : BaseActivity() {

    private val mainBottomBar: BottomNavigationBar by bindView(R.id.main_bottom_bar)

    private var selectedPosition: Int = 0

    /**
     * 主页所有界面
     */
    private var mainFragmentList: MutableList<Fragment>? = ArrayList()

    /**
     * 布局文件ID
     */
    override fun getLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        super.initViews()
        //底部导航
        initBottomNavigationBar()
        //fragment
        initFrameLayout()
    }

    private fun initFrameLayout(){
        if (mainFragmentList == null) {
            mainFragmentList = ArrayList()
        } else {
            mainFragmentList!!.clear()
        }
        mainFragmentList!!.add(MainFragment.newInstance("首页"))
        mainFragmentList!!.add(MainFragment.newInstance("分类"))
        mainFragmentList!!.add(MainFragment.newInstance("全部商品"))
        mainFragmentList!!.add(MainFragment.newInstance("购物车"))
        mainFragmentList!!.add(MainFragment.newInstance("我的"))

        if (mainFragmentList!!.size > selectedPosition) {
            val fragmentManager = supportFragmentManager
            val ft = fragmentManager.beginTransaction()
            ft.add(R.id.main_frameLayout, mainFragmentList!![selectedPosition]).commit()
        }
    }

    private fun initBottomNavigationBar() {
        mainBottomBar.setTabSelectedListener(onTabSelectedListener)
        mainBottomBar.clearAll()
        mainBottomBar.setMode(BottomNavigationBar.MODE_FIXED)
        mainBottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        //背景颜色
        mainBottomBar.setBarBackgroundColor(R.color.color_ffffff)
        //主页
        val itemMain:BottomNavigationItem = BottomNavigationItem(R.drawable.icon_main1, R.string.myy)
            .setInactiveIconResource(R.drawable.icon_main_un1)
            .setActiveColorResource(R.color.color_333333)
        //分类
        val itemSort:BottomNavigationItem = BottomNavigationItem(R.drawable.icon_sort1, R.string.sort)
            .setInactiveIconResource(R.drawable.icon_sort_un1)
            .setActiveColorResource(R.color.color_333333)
        //全部商品
        val itemAll:BottomNavigationItem = BottomNavigationItem(R.drawable.icon_all_goods1, R.string.all_commodity)
            .setInactiveIconResource(R.drawable.icon_all_goods_un1)
            .setActiveColorResource(R.color.color_333333)
        //购物车
        val itemCars:BottomNavigationItem = BottomNavigationItem(R.drawable.icon_shop1, R.string.shop)
            .setInactiveIconResource(R.drawable.icon_shop_un1)
            .setActiveColorResource(R.color.color_333333)
        //我的
        val itemMine:BottomNavigationItem = BottomNavigationItem(R.drawable.icon_mine1, R.string.my)
            .setInactiveIconResource(R.drawable.icon_mine_un1)
            .setActiveColorResource(R.color.color_333333)
        mainBottomBar
            .addItem(itemMain)
            .addItem(itemSort)
            .addItem(itemAll)
            .addItem(itemCars)
            .addItem(itemMine)
            .setInActiveColor(R.color.color_999999)
            .setFirstSelectedPosition(selectedPosition)
            .initialise()
    }

    private val onTabSelectedListener = object : BottomNavigationBar.OnTabSelectedListener {
        override fun onTabSelected(position: Int) {
            showCurrentFragment(position)
        }

        override fun onTabUnselected(position: Int) {
        }

        override fun onTabReselected(position: Int) {
        }
    }


    /**
     * 根据坐标显示某一个界面
     * @param pos 坐标
     */
    private fun showCurrentFragment(pos: Int) {
        if (mainFragmentList!!.size > pos) {
            if (selectedPosition != pos) {
                val fm = supportFragmentManager
                //开启事务
                val transaction = fm.beginTransaction()
                val lastFragment = mainFragmentList!![selectedPosition]
                //隐藏其它界面
                transaction.hide(lastFragment)
                //显示目标界面
                val fragment = mainFragmentList!![pos]
                // 先判断是否被add过
                if (!fragment.isAdded) {
                    // 隐藏当前的fragment
                    transaction.add(R.id.main_frameLayout, fragment)
                    transaction.commit()
                } else {
                    //提交 commit 不行，因为如果没在此界面，commit无效,
                    // 两者的作用都是提交Fragment的commit操作，commitAllowingStateLoss允许丢失一些界面的状态和信息
                    //比如fragment因为某些原因被销毁，同时此操作可能丢失一些信息，需要时自己储存吧
                    transaction.show(fragment)
                    transaction.commitAllowingStateLoss()
                }
                selectedPosition = pos
            }
        }
    }
}
