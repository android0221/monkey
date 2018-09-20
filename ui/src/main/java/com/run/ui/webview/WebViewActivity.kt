package com.run.ui.webview

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.ViewGroup
import android.webkit.*
import com.run.common.base.BaseActivity
import com.run.common.utils.ULog
import com.run.ui.R


@Suppress("JAVA_CLASS_ON_COMPANION")
/**
 * 加载网页数据
 */
class WebViewActivity : BaseActivity<Nothing>() {
    companion object {
        fun newInstance(context: Context) {
            val TAG = WebViewActivity.javaClass.name
            context.startActivity(Intent(context, WebViewActivity::class.java))
        }
    }

    override fun initContentView(): Int {
        return R.layout.activity_web_view
    }

    private lateinit var webView: WebView
    override fun initViews() {
        webView = findViewById(R.id.contentWebView)
        initWebView()
    }

    override fun initData() {
    }

    override fun initPresenter(): Nothing? {
        return null
    }

    //======================================初始化webveiw=====================================================================
    private lateinit var webSettings: WebSettings
    private val url: String = "http://www.vhlf4.cn./n1012dBf0506c5ce0r99_0302350KYT100ce8b79834445b4590gH-1008fb2245afcce2d170k3_0f02fe5b71e11dc56f7"
    //    private val url: String = "http://a.dfkjpx.cn/bd/eltric/drm/due/1081878/1536741224.html"
    private fun initWebView() {
        webSettings = webView.settings
        webSettings.javaScriptEnabled = true //支持js
        webSettings.useWideViewPort = false //将图片调整到合适webview的大小
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN  //支持内容重新布局
        webSettings.supportMultipleWindows()  //多窗口
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK  //关闭webview中缓存
        webSettings.loadsImagesAutomatically = true //支持自动加载图片
        webSettings.javaScriptCanOpenWindowsAutomatically = true//支持通过JS打开新窗口
        webSettings.allowFileAccess = true //设置可以访问文件
        webSettings.setNeedInitialFocus(true) //当webview调用requestFocus时为webview设置节点
        webView.loadUrl(url)
        //设置不用系统浏览器打开，直接显示在当前的webView
        webView.webViewClient = object : WebViewClient() {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                ULog.d(TAG, request!!.url)
                view!!.loadUrl(request.url.toString())
                return true
            }

            //开始加载
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                ULog.d(TAG, "开始加载")
            }

            //结束加载
            override fun onPageFinished(view: WebView?, url: String?) {
                ULog.d(TAG, "结束加载")
            }
        }
        webView.webChromeClient = object : WebChromeClient() {

            /**
             * 获取网站标题
             */
            override fun onReceivedTitle(view: WebView?, title: String?) {
                ULog.d(TAG, "网站标题 -> $title")
            }

            /**
             * 获取网站进度
             */
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                ULog.d(TAG, "加载进度 -> $newProgress")

            }
        }
        val ua: String = webSettings.userAgentString
        ULog.d(TAG, "user_agent -> $ua")
//      webSettings.userAgentString = ("$ua  MicroMessenger/6.0.2.56_r958800.520")
        webSettings.userAgentString = (userAgent)
    }

    private val userAgent = "Mozilla/5.0 (Linux; Android 5.0; SM-N9100 Build/LRX21V) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile Safari/537.36 MicroMessenger/6.0.2.56_r958800.520 NetType/WIFI"
    /**
     * Mozilla/5.0 (Linux; Android 8.0.0; MHA-AL00 Build/HUAWEIMHA-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.91 Mobile Safari/537.36
     * Mozilla/5.0 (Linux; Android 5.0; SM-N9100 Build/LRX21V) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile Safari/537.36 MicroMessenger/6.0.2.56_r958800.520 NetType/WIFI
     */

    override fun onDestroy() {
    //if (webView != null) {
// webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
//            webView.clearHistory()
//            (webView.parent as ViewGroup).removeView(webView)
//            webView.destroy()
//        }
        super.onDestroy()
    }


}
