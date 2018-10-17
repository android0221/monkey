package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.presenter.contract.TransmitContract
import com.run.presenter.modle.TransmitModle
import com.run.ui.R
import com.run.ui.adapter.ArtiveAdapter
import kotlinx.android.synthetic.main.activity_transmit.*

class TransmitActivity : BaseActivity<TransmitContract.TransmitPresenter>(), TransmitContract.TransmitView {


    companion object {
        fun newInstance(context: Context, userID: Int) {
            val intent = Intent(context, TransmitActivity::class.java)
            intent.putExtra("userID", userID)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_transmit
    }


    private lateinit var mAdapter: ArtiveAdapter
    private lateinit var headView: View
    private lateinit var footView: View
    private lateinit var dayView: TextView
    private lateinit var explainWebView: WebView
    private lateinit var prizeexplainWebView: WebView
    override fun initViews() {
        backView.setOnClickListener { finish() }

        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
        mAdapter = ArtiveAdapter()
        recyclerview.adapter = mAdapter

        headView = View.inflate(this, R.layout.layout_transmit_header, null)
        footView = View.inflate(this, R.layout.layout_active_foot, null)
        dayView = headView.findViewById(R.id.dayView)

        mAdapter.addHeaderView(headView)
        mAdapter.addFooterView(footView)


        prizeexplainWebView = headView.findViewById(R.id.prizeexplainWebView)
        explainWebView = footView.findViewById(R.id.explainWebView)

    }

    override fun initData() {
        mPresenter!!.requestData()
        val userid = intent.getIntExtra("userID", 0)
        dayView.text = "您(ID:$userid)本期转发收入如下"
    }

    override fun initPresenter(): TransmitContract.TransmitPresenter? {
        return TransmitContract.TransmitPresenter(this)
    }

    override fun showData(modle: TransmitModle) {
        //活动结束
        if (modle.prize_type == 0) {
            prizeexplainWebView.visibility = View.VISIBLE
        }
        prizeexplainWebView.loadDataWithBaseURL(null, modle.prize_explain, "text/html", "utf-8", null)
        explainWebView.loadDataWithBaseURL(null, modle.explain, "text/html", "utf-8", null)

        mAdapter.modifyColor = true
        mAdapter.setNewData(modle.list)

    }

}
