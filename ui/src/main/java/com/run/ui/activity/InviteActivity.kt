package com.run.ui.activity

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.run.common.base.BaseActivity
import com.run.common.dialog.DialogHelper
import com.run.common.utils.UGlide
import com.run.presenter.contract.InviteContract
import com.run.presenter.modle.InviteModle
import com.run.share.UShare
import com.run.ui.R
import kotlinx.android.synthetic.main.activity_invite.*
import kotlinx.android.synthetic.main.layout_invite_header.*


@Suppress("DEPRECATION", "DEPRECATED_IDENTITY_EQUALS")
/**
 * 收徒邀请页面
 */
class InviteActivity : BaseActivity<InviteContract.InvitePresenter>(), InviteContract.InviteView {

    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, InviteActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_invite
    }

    override fun initViews() {
        ll_share_wc.setOnClickListener(this)
        ll_share_wcfriend.setOnClickListener(this)
        ll_share_face.setOnClickListener(this)
        ll_share_copy.setOnClickListener(this)
        iv_sm.setOnClickListener(this)
        iv_invite_top.setOnClickListener(this)

        findViewById<View>(R.id.tv_st).setOnClickListener(this)
        findViewById<View>(R.id.tv_back).setOnClickListener(this)
    }



    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_back -> finish()
            R.id.ll_share_wc -> {
                if (shareBean == null) return
                UShare.doShare(this, "wechat_friend", shareBean!!.title!!,
                        shareBean!!.content_describe!!, url!!, shareBean!!.share_picture!!, 0)
            }
            R.id.ll_share_wcfriend -> {
                if (shareBean == null) return
                UShare.doShare(this, "wechat_moments", shareBean!!.title!!,
                        shareBean!!.content_describe!!, url!!, shareBean!!.share_picture!!, 1)
            }
            R.id.ll_share_copy -> doCopy()
            R.id.iv_invite_top -> ContestActivity.newInstance(this)
            R.id.iv_sm -> ProblemActivity.newInstance(this, 2)
            R.id.ll_share_face -> {
                if (shareBean == null) return
                FaceInviteActivity.newInstance(this, url!!, shareBean!!.title!!,
                        shareBean!!.content_describe!!, shareBean!!.share_picture!!)
            }
            R.id.tv_st -> ApprenticeListActivity.newInstance(this, explain)
        }
    }

    //=============================数据操作==========================================================
    override fun initPresenter(): InviteContract.InvitePresenter? {
        return InviteContract.InvitePresenter(this)
    }

    override fun initData() {
        mPresenter!!.invite()
    }

    /**
     * 复制链接
     */
    private fun doCopy() {
        if (shareBean == null) return
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.text = shareBean!!.title + ";" + url
        showMsg("链接复制成功")
    }

    private var explain: String? = ""
    private var shareBean: InviteModle.ShareBean? = null
    private var url: String? = null
    private var friendUrl: String? = null
    override fun showInvite(modle: InviteModle) {
        if (modle.code == 10003) {//账号被封异常
            showMsg(modle.msg)
            finish()
            return
        }
        url = modle.url
        friendUrl = modle.friend_url
        shareBean = modle.share
        tv_count_invite.text = modle.count_invite.toString()
        tv_list.text = modle.list.toString()
        tv_count_apprentice.text = if (TextUtils.isEmpty(modle.count_apprentice)) "0.0" else modle.count_apprentice + ""
        tv_count_all.text = if (TextUtils.isEmpty(modle.count_all)) "0.0" else modle.count_all + ""

        UGlide.loadImage(this, modle.invite_top_img!!, this.iv_invite_top)
        iv_invite_top.isEnabled = modle.activity_type !== 0

        explain = modle.explain
        //收徒说明
        webView.loadDataWithBaseURL(null, modle.explain, "text/html", "utf-8", null)
    }
}
