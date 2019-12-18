package com.aillience.commlibrary.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author yfl
 * viewPager的简单适配器
 */
class BasePagerAdapter(
    fm: FragmentManager,
    private val fragmentList: List<Fragment>,
    private val list: List<String>?
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return list?.size ?: 0
    }

    /**
     * //此方法用来显示tab上的名字
     *
     * @param position 坐标
     * @return 返回
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return list!![position]
    }
}
