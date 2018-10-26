package com.run.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import com.run.common.BaseApplication
import com.run.common.base.BaseActivity
import com.run.common.emoj.EmotionUtils
import com.run.common.emoj.SpanStringUtils
import com.run.common.utils.ULog
import com.run.common.utils.UStatusBar
import com.run.common.utils.UWebView
import com.run.presenter.contract.ArticleDetailContract
import com.run.presenter.modle.ArticleDetailModle
import com.run.share.ShareHelper
import com.run.ui.R
import com.run.ui.adapter.ArticleMoreAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * 文章详情页面
 */
class ArticleDetailActivity : BaseActivity<ArticleDetailContract.ArticlePresenter>(), ArticleDetailContract.ArticleView {

    companion object {
        fun newInstance(context: Context, articled: Int, money: String) {
            var intent = Intent(context, ArticleDetailActivity::class.java)
            intent.putExtra("ARTICLEID", articled)
            intent.putExtra("MONEY", money)
            context.startActivity(intent)
        }

        const val TAG = "ArticleDetailActivity"
    }


    override fun initContentView(): Int {
        return R.layout.activity_article_detail
    }

    private lateinit var shareMoneyView: TextView
    private lateinit var hintView: ImageView
    private lateinit var titleView: TextView
    private lateinit var contentWebView: WebView
    private lateinit var moreLayout: View
    @SuppressLint("AddJavascriptInterface")
    override fun initViews() {
        shareMoneyView = findViewById(R.id.tv_sharemsg)
        hintView = findViewById(R.id.hintView)
        initRecyclerView()

        titleView = findViewById(R.id.titleView)
        //初始化webview
        contentWebView = findViewById(R.id.contentWebView)
        UWebView.initWebView(contentWebView)

        UWebView.initImageClick(contentWebView)
        contentWebView.addJavascriptInterface(object : JsCallJavaObj {
            @JavascriptInterface
            override fun showBigImg(url: String) {
                ULog.d(TAG, "showBigImg :$url")
                openToImageActivity(url)
            }
        }, "jsCallJavaObj")


        moreLayout = findViewById(R.id.moreLayout)
        findViewById<ImageView>(R.id.iv_return).setOnClickListener(this)
        findViewById<View>(R.id.iv_share).setOnClickListener(this)
        findViewById<View>(R.id.ll_share_wc).setOnClickListener(this)
        findViewById<View>(R.id.ll_share_wc_friend).setOnClickListener(this)
        findViewById<View>(R.id.iv_float_share).setOnClickListener(this)
        hintView.setOnClickListener(this)
    }

    /**
     * Js調用Java接口
     */
    private interface JsCallJavaObj {
        fun showBigImg(url: String)
    }


    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: ArticleMoreAdapter
    private fun initRecyclerView() {
        recyclerview = findViewById(R.id.recyclerview)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(BaseApplication.context)
        adapter = ArticleMoreAdapter()
        recyclerview.adapter = adapter
    }

    /**
     * 查看图片
     */
    @SuppressLint("CheckResult")
    private fun openToImageActivity(url: String) {
        if (imgageList == null || imgageList!!.isEmpty()) return
        val position = imgageList!!.indexOf(url)
        if (position >= 0) {
            Observable.just("").observeOn(AndroidSchedulers.mainThread()).subscribe {
                PhotoViewActivity.newInstance(this@ArticleDetailActivity, imgageList as ArrayList<String>, position, title, articleid!!, money)
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_return -> finish()
            R.id.iv_share, R.id.iv_float_share, R.id.hintView -> ShareHelper.instance.doShare(this, articleid!!, money)
            R.id.ll_share_wc -> ShareHelper.instance.requestShare(this, 1, articleid!!)
            R.id.ll_share_wc_friend -> ShareHelper.instance.requestShare(this, 2, articleid!!)
        }
    }


    //=====================================数据操作==========================================
    override fun initPresenter(): ArticleDetailContract.ArticlePresenter? {
        return ArticleDetailContract.ArticlePresenter(this)
    }

    var articleid: Int? = null
    private lateinit var money: String
    override fun initData() {
        articleid = intent.getIntExtra("ARTICLEID", 0)
        money = intent.getStringExtra("MONEY")
        shareMoneyView.text = "+" + money + "元/位→"
        mPresenter!!.requestData(articleid!!)
    }


    private var imgageList: List<String>? = null
    private lateinit var title: String
    override fun callBackData(modle: ArticleDetailModle) {
        val data = modle.data ?: return
        title = data.title!!
        titleView.text = SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_CLASSIC_TYPE, this, titleView, title)
        when (data.content_type) {

            1 -> {
                imgageList = UWebView.getImagePath(data.content!!)
                contentWebView.loadDataWithBaseURL(null, UWebView.getNewContent(data.content!!), "text/html", "utf-8", null)
            }

            3, 4 -> {
                contentWebView.loadUrl(data.frame_url)
                contentWebView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        view.loadUrl(url)// 使用当前WebView处理跳转
                        return false//true表示此事件在此处被处理，不需要再广播
                    }
                    override//转向错误时的处理
                    fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                        ULog.e(TAG, "文章的url加载错误： errorCode:$errorCode,description $description,failingUrl:$failingUrl")
                    }
                }
            }
        }
        /**
         * 延迟显示
         */
        Observable.timer(800, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            moreLayout.visibility = View.VISIBLE
            if (modle.list != null && modle.list!!.isNotEmpty()) {
                adapter!!.setNewData(modle.list)
            }
        }
        showShareHint()
    }

    //================================================显示分享内容===============================================
    private var hasHint: Boolean = false
    private fun showShareHint() {
        if (money.toDouble() == 0.2) {
            Observable.timer(5, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
                if (!hasHint) {
                    hasHint = true
                    hintView.visibility = View.VISIBLE
                    Observable.timer(5, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
                        hintView.visibility = View.GONE
                    }
                }
            }
        }

    }

}
