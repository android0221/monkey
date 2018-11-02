package com.run.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.run.common.BaseApplication
import com.run.common.base.BaseActivity
import com.run.common.emoj.EmotionUtils
import com.run.common.emoj.SpanStringUtils
import com.run.common.utils.UGlide
import com.run.common.utils.UStatusBar
import com.run.common.utils.UWebView
import com.run.presenter.contract.ArticleDetailContract
import com.run.presenter.modle.ArticleBean
import com.run.presenter.modle.ArticleDetailModle
import com.run.share.ShareHelper
import com.run.ui.ArticleHelper
import com.run.ui.R
import com.run.ui.adapter.ArticleMoreAdapter
import com.yun.uvedio.JZVideoPlayerStandard

/**
 * 视频详情页面
 */
class VedioDetailActivity : BaseActivity<ArticleDetailContract.ArticlePresenter>(), ArticleDetailContract.ArticleView {

    companion object {
        fun newInstance(context: Context, articled: Int, url: String, money: String) {
            var intent = Intent(context, VedioDetailActivity::class.java)
            intent.putExtra("ARTICLEID", articled)
            intent.putExtra("IMAGEURL", url)
            intent.putExtra("MONEY", money)
            context.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_vedio_detail
    }

    private lateinit var jzVideoPlayerStandard: JZVideoPlayerStandard
    private lateinit var webView: WebView
    private lateinit var titleView: TextView
    override fun initViews() {
        //设置状态栏黑色字体
        UStatusBar.setDarkMode(this@VedioDetailActivity)
        jzVideoPlayerStandard = findViewById(R.id.videoplayer)
        webView = findViewById(R.id.webView)
        UWebView.initWebView(webView)
        titleView = findViewById(R.id.titleView)
        initRecycler()
        /**
         * 什么事需求，需求是功能，用户要的功能
         */
        findViewById<ImageView>(R.id.iv_return).setOnClickListener(this)
        findViewById<ImageView>(R.id.iv_share).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_return -> finish()
            R.id.ll_share_wc -> ShareHelper.instance.requestShare(this, 1, articleid!!)
            R.id.ll_share_wc_friend -> ShareHelper.instance.requestShare(this, 2, articleid!!)
            R.id.iv_share -> ShareHelper.instance.doShare(this, articleid!!, money)
        }
    }

    var articleid: Int? = null
    private var imageUrl: String? = null
    private lateinit var money: String
    override fun initData() {
        imageUrl = intent.getStringExtra("IMAGEURL")
        articleid = intent.getIntExtra("ARTICLEID", 0)
        money = intent.getStringExtra("MONEY")
        mPresenter!!.requestData(articleid!!)
    }

    override fun onBackPressed() {
        if (JZVideoPlayerStandard.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        //处理用webview播放视频是退出页面声音还在的情况
        if (webView != null) {
            webView.reload()
        }
        if (jzVideoPlayerStandard != null) {
            JZVideoPlayerStandard.releaseAllVideos()
        }
    }

    override fun initPresenter(): ArticleDetailContract.ArticlePresenter? {
        return ArticleDetailContract.ArticlePresenter(this)
    }

    override fun callBackData(modle: ArticleDetailModle) {
        val data = modle.data
        when (data!!.content_type) {
            2 -> {
                if (jzVideoPlayerStandard != null) {
                    webView!!.visibility = View.GONE
                    jzVideoPlayerStandard!!.visibility = View.VISIBLE
                    UGlide.loadImage(this, imageUrl!!, jzVideoPlayerStandard!!.thumbImageView)
                    jzVideoPlayerStandard!!.setUp(if (TextUtils.isEmpty(data.frame_url)) data.content else data.frame_url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "")
                    jzVideoPlayerStandard!!.startVideo()
                }
            }
            3, 4 -> {
                jzVideoPlayerStandard!!.visibility = View.GONE
                webView!!.visibility = View.VISIBLE
                webView!!.loadUrl(UWebView.getVedioUrl(data.frame_url!!))
            }
        }
        titleView!!.text = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, this, titleView, data.title)
        ll_share!!.visibility = View.VISIBLE
        if (modle.list != null && modle.list!!.isNotEmpty()) {
            ll_artice_more!!.visibility = View.VISIBLE
            adapter!!.setNewData(modle.list)
        } else {
            ll_artice_more!!.visibility = View.VISIBLE
            adapter!!.setNewData(ArticleHelper.instance.getList())
        }
    }

    //====================================热门推荐=============================================================================
    var recyclerView: RecyclerView? = null
    var adapter: ArticleMoreAdapter? = null
    var headView: View? = null
    private var ll_share: View? = null
    var ll_artice_more: View? = null
    fun initRecycler() {
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(BaseApplication.context)
        adapter = ArticleMoreAdapter()
        recyclerView!!.adapter = adapter

        headView = View.inflate(this, R.layout.layout_article_header, null)
        if (adapter != null) {
            adapter!!.addHeaderView(headView)
        }
        ll_share = headView!!.findViewById(R.id.ll_share)
        ll_artice_more = headView!!.findViewById(R.id.ll_artice_more)

        headView!!.findViewById<View>(R.id.ll_share_wc).setOnClickListener(this)
        headView!!.findViewById<View>(R.id.ll_share_wc_friend).setOnClickListener(this)
    }

}
