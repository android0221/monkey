package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.text.Html
import com.run.common.base.BaseActivity
import com.run.common.base.BaseObserver
import com.run.common.utils.UWebView
import com.run.login.api.LoginManager
import com.run.presenter.modle.login.DyContentModle
import com.run.ui.R
import com.run.ui.R.id.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_explain.*

@Suppress("DEPRECATION")
class ExplainActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, ExplainActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_explain
    }

    override fun initViews() {
        xszdView.setOnClickListener { ProblemActivity.newInstance(this, 1) }
        apprenticeButton.setOnClickListener { InviteActivity.newInstance(this) }
//        UWebView.initWebView(wb_content)
        backView.setOnClickListener { finish() }
        zfButton.setOnClickListener { finish() }
    }

    override fun initData() {
//        val hbMsg = " 注册奖励<font color='#ff0000'>1元</font>"
//        hbView.text = Html.fromHtml(hbMsg)
//
//        val zfMsg = "单价 <font color ='#ff0000'>0.2元</font> <br>会员转发到朋友圈,微信群, <br>每有一次有效阅读会员得<font color ='#ff0000'>0.2元</font> <br>阅读是否有效由系统自动判定 "
//        zfView.text = Html.fromHtml(zfMsg)
//
//        val ApprenticeMsg = "每收1个徒弟，最多可得 <font color ='#ff0000'>22元</font>  <br>" +
//                "徒弟转发收益满<font color='#ff0000'>1元</font>奖<font color='#ff0000'>1元</font> <br> " +
//                "徒弟转发收益满<font color='#ff0000'>2元</font>再奖<font color='#ff0000'>1元</font> <br>" +
//                "徒弟首次提现成功奖励<font color='#ff0000'>2元</font> <br>" +
//                "徒弟转发文章总收益满<font color='#ff0000'>30</font>奖<font color='#ff0000'>3元</font> <br>" +
//                "徒弟转发文章总收益满<font color='#ff0000'>50</font>奖<font color='#ff0000'>5元</font> <br>" +
//                "徒弟转发文章总收益满<font color='#ff0000'>100</font>奖<font color='#ff0000'>10元</font>"
//        ApprenticeView.text = Html.fromHtml(ApprenticeMsg)
//
//        val lineMsg = "共<font color ='#ff0000'>15%</font>师傅可获得徒弟转发收益的<font color ='#ff0000'>10%</font>, <br>徒孙转发收益的<font color ='#ff0000'>5%</font>"
//        downlineView.text = Html.fromHtml(lineMsg)
//
//        val signMsg = "每天转发收入满2元可签到，连续签到： <br> 1天得<font color ='#ff0000'>0.20元</font> <br>" +
//                " 2天得<font color ='#ff0000'>0.25元</font> <br>" +
//                " 3天得<font color ='#ff0000'>0.30元</font><br>" +
//                " 4天得<font color ='#ff0000'>0.35元</font><br>" +
//                " 5天得<font color ='#ff0000'>0.40元</font><br>" +
//                " 6天得<font color ='#ff0000'>0.45元</font><br>" +
//                " 7天以上得<font color ='#ff0000'>0.50元</font><br>" +
//                " 断掉重新计算"
//        signView.text = Html.fromHtml(signMsg)
//
//        val withdrawMsg = "第一次提现<font color ='#ff0000'>5元</font>起提现，第2次以后<font color ='#ff0000'>10元</font>起提现"
//        withdrawView.text = Html.fromHtml(withdrawMsg)


        requestData()
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    fun requestData() {
        LoginManager.problemContent(12).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<DyContentModle>() {
                    override fun onError(errorType: Int, msg: String?) {
                        showErr(errorType, msg!!)
                    }
                    override fun onSuccess(model: DyContentModle) {
                        titleView.text = model.data!!.title
                        val contentMsg = model.data!!.content!!.replace("949", "100%")
                        wb_content!!.loadDataWithBaseURL(null, UWebView.getNewContent3(contentMsg), "text/html", "utf-8", null)
                    }
                })
    }

}
