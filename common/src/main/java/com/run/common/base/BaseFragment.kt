package com.run.common.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.run.common.dialog.LoadingDialog

abstract class BaseFragment<T : BaseMvpPresenter> : Fragment(), BaseMvpView, View.OnClickListener {
    /**
     * 初始化加载对话框
     */
    private val mDialog by lazy {
        let { LoadingDialog.create(activity!!) }
    }


    //===========================================Fragment可见 开始===================================
    private var isWaitingForOnMyResume = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View? {
        val view = inflater.inflate(initContentView(), null)
        setPresenter()
        initView(view)
        initData()
        return view
    }


    //===============================初始化presenter=================================================
    var mPresenter: T? = null

    private fun setPresenter() {
        mPresenter = initPresenter()
    }

    //=========================数据初始化============================================================
    protected abstract fun initContentView(): Int

    protected abstract fun initPresenter(): T?
    protected abstract fun initView(view: View)
    protected abstract fun initData()
    //==========================================数据加载=============================================
    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        if (mDialog.isShowing) mDialog.hide()
    }

    override fun showErr(errorType: Int, msg: String) {
        showMsg(msg)
    }

    override fun showMsg(msg: String?) {
        if (TextUtils.isEmpty(msg)) return
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {}

    //===========================================Fragment可见 开始===================================
    override fun onResume() {
        super.onResume()
        if (isWaitingForOnMyResume) {
            isWaitingForOnMyResume = false
            visiable()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (isResumed) {
                visiable()
            } else {
                isWaitingForOnMyResume = true
            }
        }
    }

    /**
     * fragment页面可见时调用；
     */
    protected open fun visiable() {}
    //===========================================Fragment可见 结束===================================


}