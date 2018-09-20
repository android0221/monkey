package com.run.common.utils

import android.os.Build
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.regex.Pattern

object UWebView {

    fun initWebView(webView: WebView) {
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        } else {
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        }
        webView.setInitialScale(100)


    }

    /**
     * 设置样式
     */
    fun getNewContent(htmltext: String): String {
        val head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>*{font-size:18px;color:#555; margin:1;padding:2; letter-spacing:2px;text-align:justify;line-height:26pt;}img{max-width: 100%; width:100%; height:auto; margin:1px; text-align:center}</style>" +
                "</head>"
        return "<html>$head<body>$htmltext</body></html>"
    }


    fun getNewContent2(htmltext: String): String {
        val head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>*{font-size:16px;color:#666; margin:0;padding:0; letter-spacing:1px;text-align:justify;line-height:22pt;}img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>"
        return "<html>$head<body>$htmltext</body></html>"
    }



    fun getVedioUrl(url: String): String {
        return "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>*{margin:0;padding:0;}</style>" +
                "<iframe frameborder=\"0\"  scrolling=\"no\" width=\"100%\" height=\"300\" src=\"" + url + "\" allowfullscreen></iframe>" +
                "</head>"
    }

    /**
     * 获取WebView中图片的路径
     */
    fun getImagePath(htmlText: String): List<String> {
        val imagePaht = ArrayList<String>()
        val p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r>]+(\\.jpg|\\.bmp|\\.gif|\\.png|\\.jpe|\\.jpeg|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE)
        val m = p.matcher(htmlText)
        var quote: String? = null
        var src: String? = null
        while (m.find()) {
            quote = m.group(1)
            src = if (quote == null || quote!!.trim { it <= ' ' }.isEmpty()) m.group(2).split("\\s+")[0] else m.group(2)
            if (src != null) {
                imagePaht.add(src)
            }

        }
        return imagePaht
    }


    /**
     * 设置样式
     */
    fun getContent(htmltext: String): String {
        val head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>*{ margin:0;padding:0; letter-spacing:1px;text-align:justify;line-height:16pt;}img{max-width: 100%; width:auto; height:auto; margin:2px}</style>" +
                "</head>"
        return "<html>$head<body>$htmltext</body></html>"
    }

    /**
     * 设置图片的点击事件
     */
    fun initImageClick(webview: WebView) {
        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val jsCode = "javascript:(function(){" +
                        "var imgs=document.getElementsByTagName(\"img\");" +
                        "for(var i=0;i<imgs.length;i++){" +
                        "imgs[i].onclick=function(){" +
                        "window.jsCallJavaObj.showBigImg(this.src);" +
                        "}}})()"
                webview!!.loadUrl(jsCode)
            }
        }
    }


}
