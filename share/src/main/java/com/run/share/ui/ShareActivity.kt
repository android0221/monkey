package com.run.share.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log

import com.run.share.R
import com.run.share.utils.Config
import com.run.share.utils.ShareManager
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb


import java.util.ArrayList


class ShareActivity : Activity() {

    private var sharePlatform: String? = null
    private var title: String? = null
    private var text: String? = null
    private var url: String? = null
    private var imageUrl: String? = null
    private var media: SHARE_MEDIA? = null
    private var sort: String? = null
    private var type = 0
    private var friend_type = 1

    internal var umShareListener: UMShareListener = object : UMShareListener {
        override fun onResult(platform: SHARE_MEDIA) {
            Log.e("share", "onResult")
        }

        override fun onError(platform: SHARE_MEDIA, throwable: Throwable) {
            Log.e("share", "onerror", throwable)
        }

        override fun onCancel(platform: SHARE_MEDIA) {
            if (!this@ShareActivity.isFinishing) {
                this@ShareActivity.finish()
            }
        }

        override fun onStart(arg0: SHARE_MEDIA) {
            Log.e("share", "onStart")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        if (intent.hasExtra("platform")) {
            this.sharePlatform = intent.getStringExtra("platform")
        }
        if (intent.hasExtra("title")) {
            this.title = intent.getStringExtra("title")
        }
        if (intent.hasExtra("describe")) {
            this.text = intent.getStringExtra("describe")
        }
        if (intent.hasExtra("link")) {
            this.url = intent.getStringExtra("link")
        }
        if (intent.hasExtra("image")) {
            this.imageUrl = intent.getStringExtra("image")
        }
        if (intent.hasExtra("sort")) {
            this.sort = intent.getStringExtra("sort")
        }
        if (intent.hasExtra("type")) {
            this.type = intent.getIntExtra("type", 0)
        }
        if (intent.hasExtra("friend_type")) {
            this.friend_type = intent.getIntExtra("friend_type", 1)
        }
        if (TextUtils.isEmpty(this.url) || TextUtils.isEmpty(this.sharePlatform)) {
            finish()
        } else if (this.sharePlatform == "wechat_moments") {
            this.media = SHARE_MEDIA.WEIXIN_CIRCLE
        } else if (this.sharePlatform == "wechat_friend") {
            this.media = SHARE_MEDIA.WEIXIN
        } else if (this.sharePlatform == "qq_mobile") {
            this.media = SHARE_MEDIA.QQ
        } else if (this.sharePlatform == "qq_zone") {
            this.media = SHARE_MEDIA.QZONE
        } else if (this.sharePlatform == "sina_weibo") {
            this.media = SHARE_MEDIA.SINA
        } else if (this.sharePlatform == "alipay_friend") {
            this.media = SHARE_MEDIA.ALIPAY
        } else {
            finish()
        }
        if (type == 1) {
            val lists = ArrayList<String>()
            lists.add(this.url!!)
            val shareManager = ShareManager(this@ShareActivity)
            shareManager.setShareImage(1, lists, this.title + "\n" + this.url, type)
            return
        }

        if (sharePlatform == "wechat_moments" && friend_type == 2) {
            val lists = ArrayList<String>()
            lists.add(this.imageUrl!!)
            val shareManager = ShareManager(this@ShareActivity)
            shareManager.setShareImage(1, lists, this.title + "\n" + this.url, type)
            return
        }
        Config.checkIfNoneShowIntall(this, sort!!)
        if (!TextUtils.isEmpty(Config.sharePkg) && !TextUtils.isEmpty(Config.shareAppId)) {

            val web = UMWeb(this.url)
            web.title = this.title
            if (!TextUtils.isEmpty(imageUrl)) {
                web.setThumb(UMImage(this, this.imageUrl!!))
            } else {
                web.setThumb(UMImage(this, R.mipmap.ic_logo))
            }
            web.description = this.text
            ShareAction(this)
                    .withText(text)
                    .withMedia(web)
                    .setPlatform(this.media).setCallback(this.umShareListener).share()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

}
