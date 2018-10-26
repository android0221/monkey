package com.run.ui.activity

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import com.run.common.base.BaseActivity
import com.run.config.AppIntentAction
import com.run.presenter.contract.CustomerContract
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_customer.*

@Suppress("DEPRECATION")
/**
 * 客服Activity
 */
class CustomerActivity : BaseActivity<CustomerContract.CustomerPresenter>(), CustomerContract.CustomerView {

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, CustomerActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_customer
    }

    override fun initViews() {
        backView.setOnClickListener { finish() }

        qqLayout.setOnClickListener { AppIntentAction.joinQQGroup(qqKey, this) }
        wcLayout.setOnClickListener {
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.text = wcKey
            showMsg("客服微信复制成功！")
        }

    }

    override fun initData() {
        mPresenter!!.getQQKey()
    }

    override fun initPresenter(): CustomerContract.CustomerPresenter? {
        return CustomerContract.CustomerPresenter(this)
    }

    private var qqKey: String? = null
    private var wcKey: String? = null
    @SuppressLint("SetTextI18n")
    override fun callBackQQKey(key: String, wechat: String) {
        qqKey = key
        wcKey = wechat
        wcView.text = wechat
    }


}
