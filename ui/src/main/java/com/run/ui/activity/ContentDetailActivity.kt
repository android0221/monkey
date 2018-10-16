package com.run.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.TextView
import com.run.common.base.BaseActivity
import com.run.common.base.BaseObserver
import com.run.common.utils.ULog
import com.run.common.utils.UWebView
import com.run.login.api.LoginManager
import com.run.presenter.modle.login.DyContentModle
import com.run.ui.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContentDetailActivity : BaseActivity<Nothing>() {

    companion object {
        fun newInstance(activity: Context, title: String, id: Int) {
            val intent = Intent(activity, ContentDetailActivity::class.java)
            intent.putExtra("TITLE", title)
            intent.putExtra("id", id)
            activity.startActivity(intent)
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_content_detail
    }


    private lateinit var wb_msg: WebView
    private var tv_title: TextView? = null
    override fun initViews() {

        findViewById<View>(R.id.iv_back).setOnClickListener { finish() }
        tv_title = findViewById(R.id.tv_title)
        wb_msg = findViewById(R.id.wb_content)


        UWebView.initWebView(wb_msg)
        UWebView.initImageClick(wb_msg)
        wb_msg.addJavascriptInterface(object : JsCallJavaObj {
            @JavascriptInterface
            override fun showBigImg(url: String) {
                ULog.d(ArticleDetailActivity.TAG, "showBigImg :$url")
                openToImageActivity(url)
            }
        }, "jsCallJavaObj")
    }

    /**
     * Js調用Java接口
     */
    private interface JsCallJavaObj {
        fun showBigImg(url: String)
    }
    private var imgageList: List<String>? = null
    /**
     * 查看图片
     */
    @SuppressLint("CheckResult")
    private fun openToImageActivity(url: String) {
        if (imgageList == null || imgageList!!.isEmpty()) return
        val position = imgageList!!.indexOf(url)
        if (position >= 0) {
            Observable.just("").observeOn(AndroidSchedulers.mainThread()).subscribe {
                PhotoViewActivity.newInstance(this@ContentDetailActivity, imgageList as ArrayList<String>, position, "", 0, "")
            }
        }
    }



    override fun initData() {
        val title = intent.getStringExtra("TITLE")
        tv_title!!.text = title
        val id = intent.getIntExtra("id", 0)
        LoginManager.problemContent(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<DyContentModle>() {
                    override fun onError(errorType: Int, msg: String?) {
                        showErr(errorType, msg!!)
                    }

                    override fun onSuccess(model: DyContentModle) {
                        val bean = model.data
                        if (bean != null) {
                            imgageList = UWebView.getImagePath(bean.content!!)
                            wb_msg!!.loadDataWithBaseURL(null, UWebView.getNewContentSetting(bean.content!!), "text/html", "utf-8", null)
                        }
                    }
                })
    }

    override fun initPresenter(): Nothing? {
        return null
    }


}
