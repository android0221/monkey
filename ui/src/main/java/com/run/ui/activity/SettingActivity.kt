package com.run.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.base.BaseObserver
import com.run.common.dialog.ClearCacherDialog
import com.run.common.dialog.DialogCallBack
import com.run.common.utils.UCache
import com.run.common.utils.UVersion
import com.run.config.modle.BaseModle
import com.run.login.api.LoginManager
import com.run.presenter.LoginHelper
import com.run.presenter.contract.SettingContract
import com.run.ui.R
import com.run.ui.login.RegisterActivity
import com.run.version.UpdataVersionHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * 设置页面
 */
class SettingActivity : BaseActivity<SettingContract.SettingPresenter>(), SettingContract.SettingView {


    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, SettingActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_setting
    }


    private var tv_cache: TextView? = null
    private var tv_version: TextView? = null
    override fun initViews() {
        tv_cache = findViewById(R.id.tv_cache)
        tv_version = findViewById(R.id.tv_version)
        findViewById<View>(R.id.ll_cache).setOnClickListener(this)
        findViewById<View>(R.id.ll_version).setOnClickListener(this)
        findViewById<View>(R.id.backView).setOnClickListener(this)
        findViewById<View>(R.id.tv_logout).setOnClickListener(this)
        findViewById<View>(R.id.passwordLayout).setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        var cache = "0M"
        try {
            cache = UCache.getTotalCacheSize(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        tv_cache!!.text = cache
        tv_version!!.text = "v" + UVersion.getLocalVersionName(this)
    }

    override fun initPresenter(): SettingContract.SettingPresenter? {
        return SettingContract.SettingPresenter(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.backView -> finish()
            R.id.ll_cache -> showClearCacheDialog()
            R.id.ll_version -> UpdataVersionHelper.getInstance().checkUpadata(this, 0)
            R.id.tv_logout -> mPresenter!!.logout()
            R.id.passwordLayout -> RegisterActivity.newInstance(this, 2, 1)
        }
    }


    private fun showClearCacheDialog() {
        ClearCacherDialog.newInstance(this@SettingActivity).show(this@SettingActivity, callBack = object : DialogCallBack {
            override fun onNext() {
                UCache.clearAllCache(this@SettingActivity)
                tv_cache!!.text = "0KB"
            }

            override fun cancle() {
            }
        })
    }

    /**
     * 退出登录返回
     */
    override fun callBackLogout(msg: String) {
        showMsg(msg)
        MainActivity.newInstance(this@SettingActivity)
    }

}
