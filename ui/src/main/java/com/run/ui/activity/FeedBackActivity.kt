package com.run.ui.activity

import android.annotation.SuppressLint
import android.content.*
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.utils.UInputManager
import com.run.config.AppIntentAction
import com.run.presenter.LoginHelper
import com.run.presenter.contract.FeedBackContract
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_customer.*

class FeedBackActivity : BaseActivity<FeedBackContract.FeedBackPresenter>(), FeedBackContract.FeedBackView {


    companion object {
        fun newInstance(context: Context) {
            val intent = Intent(context, FeedBackActivity::class.java)
            context.startActivity(intent)
        }

    }


    override fun initContentView(): Int {
        return R.layout.activity_feed_back
    }

    private var et_content: EditText? = null
    private var tv_ok: TextView? = null
    override fun initViews() {
        et_content = findViewById(R.id.et_content)
        tv_ok = findViewById(R.id.tv_title)
        tv_ok!!.setOnClickListener(this)
        findViewById<View>(R.id.iv_back).setOnClickListener { finish() }


        qqLayout.setOnClickListener { AppIntentAction.joinQQGroup(qqKey, this) }
        wcLayout.setOnClickListener {
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.text = wcKey
            showMsg("客服微信复制成功！")
            getWechatApi()
        }
    }

    private var qqKey: String? = null
    private var wcKey: String? = null
    @SuppressLint("SetTextI18n")
    override fun callBackQQKey(key: String, wechat: String) {
        qqKey = key
        wcKey = wechat
        wcView.text = wechat
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_title -> submit()
        }
    }

    override fun initData() {
        mPresenter!!.getQQKey()
    }

    override fun initPresenter(): FeedBackContract.FeedBackPresenter? {
        return FeedBackContract.FeedBackPresenter(this)
    }

    private fun submit() {
        val content = et_content!!.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(content)) {
            showMsg("内容不能为空,请编辑反馈内容！")
            return
        }
        mPresenter!!.feedBack("", content, LoginHelper.instance.getmMobile()!!)
    }

    override fun submitSucdess(msg: String) {
        UInputManager.closeKeybord(et_content!!, this)
        showMsg(msg)
        et_content!!.setText("")
    }


    private fun getWechatApi() {
        try {
            val intent = Intent(Intent.ACTION_MAIN)
            val cmp = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.component = cmp
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            showMsg("检查到您手机没有安装微信，请安装后使用该功能")
        }
    }

}
