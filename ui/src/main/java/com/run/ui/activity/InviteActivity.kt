package com.run.ui.activity

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.utils.UGlide
import com.run.common.utils.UTabLayout
import com.run.presenter.contract.InviteContract
import com.run.presenter.modle.InviteModle
import com.run.presenter.modle.SeniorityModle
import com.run.share.UShare
import com.run.ui.R
import com.run.ui.adapter.SeniorityAdapter

@Suppress("DEPRECATION")
/**
 * 收徒邀请页面
 */
class InviteActivity : BaseActivity<InviteContract.InvitePresenter>(), InviteContract.InviteView, TabLayout.OnTabSelectedListener {


    companion object {
        fun newInstance(context: Context) {
            context.startActivity(Intent(context, InviteActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_invite
    }

    private lateinit var tv_count_invite: TextView
    private lateinit var tv_list: TextView
    private lateinit var tv_count_apprentice: TextView
    private lateinit var tv_count_all: TextView
    private lateinit var iv_invite_top: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout
    private lateinit var mAdapter: SeniorityAdapter
    override fun initViews() {
        mRecyclerView = findViewById(R.id.recyclerview)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = SeniorityAdapter()
        initHeardView()
        mRecyclerView.adapter = mAdapter
        findViewById<View>(R.id.tv_st).setOnClickListener(this)
        findViewById<View>(R.id.tv_back).setOnClickListener(this)
    }

    private fun initHeardView() {
        val headView = View.inflate(this, R.layout.layout_invite_header, null)

        headView.findViewById<View>(R.id.ll_share_wc).setOnClickListener(this)
        headView.findViewById<View>(R.id.ll_share_wcfriend).setOnClickListener(this)
        headView.findViewById<View>(R.id.ll_share_face).setOnClickListener(this)
        headView.findViewById<View>(R.id.ll_share_copy).setOnClickListener(this)
        headView.findViewById<View>(R.id.iv_sm).setOnClickListener(this)
        iv_invite_top = headView.findViewById(R.id.iv_invite_top)
        iv_invite_top.setOnClickListener(this)
        tv_count_invite = headView.findViewById(R.id.tv_count_invite) as TextView
        tv_list = headView.findViewById(R.id.tv_list) as TextView
        tv_count_apprentice = headView.findViewById(R.id.tv_count_apprentice) as TextView
        tv_count_all = headView.findViewById(R.id.tv_count_all) as TextView
        tabLayout = headView.findViewById(R.id.tab)
        tabLayout.addOnTabSelectedListener(this)
        UTabLayout.setTabLayoutLine(tabLayout)
        mAdapter.addHeaderView(headView)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_back -> finish()
            R.id.ll_share_wc -> {
                if (shareBean == null) return
                UShare.doShare(this, "wechat_friend", shareBean!!.title!!, shareBean!!.content_describe!!, url!!, shareBean!!.share_picture!!, 0)
            }
            R.id.ll_share_wcfriend -> {
                if (shareBean == null) return
                UShare.doShare(this, "wechat_moments", shareBean!!.title!!, shareBean!!.content_describe!!, url!!, shareBean!!.share_picture!!, 1)
            }
            R.id.ll_share_copy -> doCopy()
            R.id.iv_invite_top -> ContestActivity.newInstance(this)
            R.id.iv_sm -> ProblemActivity.newInstance(this, 2)
            R.id.ll_share_face -> {
                if (shareBean == null) return
                FaceInviteActivity.newInstance(this, url!!, shareBean!!.title!!, shareBean!!.content_describe!!, shareBean!!.share_picture!!)
            }

            R.id.tv_st -> ApprenticeListActivity.newInstance(this)
        }
    }


    //=============================数据操作==========================================================
    override fun initPresenter(): InviteContract.InvitePresenter? {
        return InviteContract.InvitePresenter(this)
    }


    private var requetType = "income"
    override fun initData() {
        mPresenter!!.invite()
        mPresenter!!.seniority(requetType)
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

    private var shareBean: InviteModle.ShareBean? = null
    private var url: String? = null
    private var friendUrl: String? = null
    override fun showInvite(modle: InviteModle) {
        url = modle.url
        friendUrl = modle.friend_url
        shareBean = modle.share

        tv_count_invite.text = modle.count_invite.toString()
        tv_list.text = modle.list.toString()
        tv_count_apprentice.text = if (TextUtils.isEmpty(modle.count_apprentice)) "0.0" else modle.count_apprentice + ""
        tv_count_all.text = if (TextUtils.isEmpty(modle.count_all)) "0.0" else modle.count_all + ""

        UGlide.loadImage(this, modle.invite_top_img!!, this!!.iv_invite_top!!)
        iv_invite_top.isEnabled = modle.activity_type !== 0

    }

    override fun showData(modle: SeniorityModle) {
        if (modle.code == 10003) {//账号被封异常
            showMsg(modle.msg)
            finish()
            return
        }
        mAdapter!!.topCount = 1
        if (modle?.list == null || modle.list!!.isEmpty()) {
            val list = arrayListOf(SeniorityModle.ListBean())
            mAdapter!!.requetType = requetType
            mAdapter!!.setNewData(list)
            return
        }
        mAdapter!!.requetType = requetType
        mAdapter!!.setNewData(modle.list)

    }

    //==================================================TabLayout回调方法=====================================================================
    override fun onTabReselected(tab: TabLayout.Tab?) {}

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabSelected(tab: TabLayout.Tab?) {
        requetType = if ("收益榜" == tab!!.text) {
            "income"
        } else {
            "gymnasiums"
        }
        mPresenter!!.seniority(requetType)
    }


}
