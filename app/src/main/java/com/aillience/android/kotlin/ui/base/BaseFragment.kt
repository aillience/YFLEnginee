package com.aillience.android.kotlin.ui.base

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder

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
abstract class BaseFragment:Fragment(){

    /**
     * 布局文件ID
     */
    internal abstract fun getLayoutResID(): Int

    /**
     * 通用接口实现
     */
    protected open var interfaceImpl: BaseInterfaceImpl? = null
    /**
     * 依赖注入绑定
     */
    private var unBinder : Unbinder? = null

    /**
     * 错误的显示
     */
    protected open var errorDialog: BaseErrorDialog?= null
    protected open var isShouldShow = false

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        //这里是错误的界面显示问题
        if(errorDialog != null){
            if(hidden){
                isShouldShow = errorDialog!!.isShowing
                if(isShouldShow){
                    errorDialog!!.dismiss()
                }
            }else{
                if(isShouldShow){
                    errorDialog!!.show()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(getLayoutResID(), container, false)
        unBinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //界面必须要它的activity
        interfaceImpl = BaseInterfaceImpl(itself())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initErrorView()
        initDataAfterOnCreate()
    }

    /**
     *当前本身Activity对象，给子类使用的
     */
    protected open fun itself(): Activity {
        return this.activity!!
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
     * 初始化错误 view
     * 这里 errorDialog 不默认初始化，在需要使用的地方重写此方法
     */
    protected open fun initErrorView(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (unBinder != null) {
            this.unBinder!!.unbind()
        }
        if(errorDialog != null){
            this.errorDialog!!.cancel()
        }
    }
}