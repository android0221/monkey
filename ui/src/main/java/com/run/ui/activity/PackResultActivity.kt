package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.View
import com.run.common.base.BaseActivity
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_pack_result.*

/**
 * 红包奖励
 */
class PackResultActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context, msg: String, money: String?) {
            val intent = Intent(context, PackResultActivity::class.java)
            intent.putExtra("MSG", msg)
            intent.putExtra("money", money)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_pack_result
    }

    override fun initViews() {
        backView.setOnClickListener { finish() }
        singStatusView.setOnClickListener { MainActivity.newInstance(this) }
        msgView.text = Html.fromHtml(intent.getStringExtra("MSG"))
        val money = intent.getStringExtra("money")
        if (money.toDouble() >= 100) {
            showMsgView.visibility = View.VISIBLE
        }
    }

    override fun initData() {
    }

    override fun initPresenter(): Nothing? {
        return null
    }


}
