package com.run.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.run.common.BaseApplication
import com.run.common.base.BaseActivity
import com.run.common.utils.UWebView
import com.run.presenter.contract.ContestContract
import com.run.presenter.modle.ContestModle
import com.run.ui.R
import com.run.ui.adapter.ContestAdapter
import java.util.*

class ContestActivity : BaseActivity<ContestContract.ContestPresenter>(), ContestContract.ContestView {

    companion object {
        fun newInstance(activity: Activity) {
            activity.startActivity(Intent(activity, ContestActivity::class.java))
        }
    }


    override fun initContentView(): Int {
        return R.layout.activity_contest
    }

    private lateinit var wb_msg: WebView
    private lateinit var wb_msg2: WebView
    private lateinit var my_nun: TextView
    private lateinit var invite_nun: TextView
    private lateinit var valid_nun: TextView
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: ContestAdapter
    private lateinit var headView: View
    private lateinit var footView: View
    override fun initViews() {
        findViewById<View>(R.id.tv_back).setOnClickListener { finish() }
        recyclerview = findViewById(R.id.recyclerview)
        recyclerview.setHasFixedSize(true)
        recyclerview.setLayoutManager(LinearLayoutManager(BaseApplication.context))
        adapter = ContestAdapter()
        recyclerview.setAdapter(adapter)

        headView = View.inflate(this, R.layout.view_contest_top_layout, null)
        wb_msg = headView.findViewById(R.id.wb_msg)
        my_nun = headView.findViewById(R.id.my_nun)
        invite_nun = headView.findViewById(R.id.invite_nun)
        valid_nun = headView.findViewById(R.id.valid_nun)

        adapter.addHeaderView(headView)

        footView = View.inflate(this, R.layout.view_contest_bottom_layout, null)
        wb_msg2 = footView.findViewById(R.id.wb_msg2)
        footView.findViewById<View>(R.id.btn_submit).setOnClickListener { InviteActivity.newInstance(this@ContestActivity) }
        adapter.addFooterView(footView)
    }


    //========================================数据操作===============================================


    override fun initPresenter(): ContestContract.ContestPresenter? {
        return ContestContract.ContestPresenter(this)
    }

    override fun initData() {
        mPresenter!!.megagame()
    }

    override fun showData(modle: ContestModle) {
        if (modle == null) return
        invite_nun!!.text = modle.valid_nun.toString()
        valid_nun!!.text = modle.my_nun
        wb_msg!!.loadDataWithBaseURL(null, UWebView.getNewContent2(modle.activity_explain!!), "text/html", "utf-8", null)
        wb_msg2!!.loadDataWithBaseURL(null, modle.activity_bottom, "text/html", "utf-8", null)


        if (modle?.list == null || modle.list!!.isEmpty()) {
            val list = ArrayList<ContestModle.ListBean>()
            list.add(ContestModle.ListBean())
            adapter!!.setNewData(list)
            return
        }
        if (adapter != null) {
            adapter.setNewData(modle.list)
        }
    }
}
