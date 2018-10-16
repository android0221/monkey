package com.run.common.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.run.common.dialog.LoadingDialog
import com.run.common.utils.UActivityManager
import com.run.common.utils.ULog

@Suppress("JAVA_CLASS_ON_COMPANION")
abstract class BaseActivity<T : BaseMvpPresenter> : AppCompatActivity(), View.OnClickListener, BaseMvpView {

    companion object {
        val TAG: String = BaseActivity.javaClass.name
    }

    /**
     * 初始化加载对话框
     */
    private val mDialog by lazy {
        let { LoadingDialog.create(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UActivityManager.addActivity(this)
        setContentView(initContentView())
        setPresenter()
        initViews()
        initData()

    }
    override fun onDestroy() {
        super.onDestroy()
        UActivityManager.removeActivity(this)
        if (mPresenter != null) {
            mPresenter = null
        }
    }

    //===========================初始化布局=====================================
    protected abstract fun initContentView(): Int

    protected abstract fun initViews()
    protected abstract fun initData()
    protected abstract fun initPresenter(): T?

    //============================初始化presenter==============================
    /**
     * mvp的presenter
     */
    var mPresenter: T? = null

    private fun setPresenter() {
        mPresenter = initPresenter()
    }

    //==============================回调方法====================================
    /**
     * 页面数据加载
     */
    override fun showLoading() {
        ULog.d(TAG, "showLoading()")
        mDialog.show()
    }
    /**
     * 取消页面加载
     */
    override fun hideLoading() {
        ULog.d(TAG, "hideLoading()")
        if (mDialog.isShowing) {
            mDialog.hide()
        }
    }
    override fun showErr(errorType: Int, msg: String) {
        showMsg(msg)
    }
    override fun showMsg(msg: String?) {
        if (TextUtils.isEmpty(msg)) return
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {}
}