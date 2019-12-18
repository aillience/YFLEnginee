package com.aillience.android.kotlin.ui.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.aillience.android.kotlin.app.MyActivityManager

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

abstract class BaseActivity : AppCompatActivity(){

    /**
     * 通用接口实现
     */
    protected open var interfaceImpl: BaseInterfaceImpl? = null

    /**
     * 依赖注入绑定
     */
    private var unBinder : Unbinder? = null
    /**
     * 布局文件ID
     */
    internal abstract fun getLayoutResID(): Int

    /**
     * 界面 onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResID())
        //绑定
        unBinder = ButterKnife.bind(this)
        MyActivityManager.newInstance().addOneActivity(this)
        //设置通用接口上下文,初始化
        interfaceImpl = BaseInterfaceImpl(itself())
        //初始化控件属性
        initViews()
        initErrorDialog()
        //开始加载其它数据了
        initDataAfterOnCreate()
    }

    /**
     *当前本身Activity对象，给子类使用的
     */
    protected open fun itself():Activity{
        return this
    }

    /**
     * 初始化一些控件属性，监听等
     */
    protected open fun initViews(){

    }

    /**
     * onCreate后的数据初始化
     * open 代表可被其它地方引用，这里我做了protected限制，仅子类可用
     * 同时，很明显open与private是冲突的
     */
    protected open fun initDataAfterOnCreate(){

    }

    /**
     * 初始化错误 dialog
     * 这里 errorDialog 不默认初始化，在需要使用的地方重写此方法
     */
    protected open fun initErrorDialog(){

    }

    /**
     * 界面 onDestroy
     */
    override fun onDestroy() {
        super.onDestroy()
        if(unBinder != null){
            unBinder!!.unbind()
        }
        MyActivityManager.newInstance().removeOneActivity(this)
    }
}