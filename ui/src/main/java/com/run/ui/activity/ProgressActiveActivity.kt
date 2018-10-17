package com.run.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.presenter.contract.ProgressActiveContract
import com.run.presenter.modle.ProgressArtiveModle
import com.run.ui.R
import com.run.ui.adapter.ArtiveAdapter
import kotlinx.android.synthetic.main.activity_progress_active.*


/**
 * 本周活动奖
 */
class ProgressActiveActivity : BaseActivity<ProgressActiveContract.ProgeressActivityPresenter>(), ProgressActiveContract.ProgressView {


    companion object {
        fun newInstance(context: Context, userID: Int) {
            val intent = Intent(context, ProgressActiveActivity::class.java)
            intent.putExtra("userID", userID)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_progress_active
    }

    private lateinit var recyclerview: RecyclerView
    private lateinit var lastrecyclerview: RecyclerView
    private lateinit var mAdapter: ArtiveAdapter
    private lateinit var lastAdapter: ArtiveAdapter

    private lateinit var headView: View
    private lateinit var footView: View

    private lateinit var dayView: TextView
    private lateinit var lastView: TextView

    private lateinit var weekexplainWebView: WebView
    private lateinit var explainWebView: WebView
    private lateinit var prizeexplainWebView: WebView

    override fun initViews() {
        findViewById<View>(R.id.backView).setOnClickListener { finish() }

        lastrecyclerview = findViewById(R.id.lastrecyclerview)
        lastrecyclerview.setHasFixedSize(true)
        lastrecyclerview.layoutManager = LinearLayoutManager(this)
        lastAdapter = ArtiveAdapter()
        lastrecyclerview.adapter = lastAdapter

        headView = View.inflate(this, R.layout.layout_active_header, null)
        footView = View.inflate(this, R.layout.layout_active_foot, null)
        lastAdapter.addHeaderView(headView)
        lastAdapter.addFooterView(footView)

        dayView = headView.findViewById(R.id.dayView)
        lastView = headView.findViewById(R.id.lastView)

        recyclerview = headView.findViewById(R.id.recyclerview)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
        mAdapter = ArtiveAdapter()
        recyclerview.adapter = mAdapter

        explainWebView = footView.findViewById(R.id.explainWebView)
        weekexplainWebView = headView.findViewById(R.id.weekexplainWebView)
        prizeexplainWebView = headView.findViewById(R.id.prizeexplainWebView)

    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val userid = intent.getIntExtra("userID", 0)
        dayView.text = "您(ID:$userid)本期转发收入如下"
        lastView.text = "您(ID:$userid)上期转发收入如下"
        mPresenter!!.requestData()
    }

    override fun initPresenter(): ProgressActiveContract.ProgeressActivityPresenter? {
        return ProgressActiveContract.ProgeressActivityPresenter(this)
    }


    override fun showData(modle: ProgressArtiveModle) {
        weekexplainWebView.loadDataWithBaseURL(null, modle.this_week_explain, "text/html", "utf-8", null)
        explainWebView.loadDataWithBaseURL(null, modle.explain, "text/html", "utf-8", null)

        //活动结束
        if (modle.prize_type == 0) {
            prizeexplainWebView.visibility = View.VISIBLE
        }
        prizeexplainWebView.loadDataWithBaseURL(null, modle.prize_explain, "text/html", "utf-8", null)

        mAdapter.setNewData(modle.this_week_list)
        lastAdapter.setNewData(modle.last_week_list)
    }

}
