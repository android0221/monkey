package com.run.share


import android.content.Context
import android.content.Intent

import com.run.share.ui.ShareActivity


object UShare {

    fun doShare(context: Context, platform: String, title: String?, describe: String?, link: String?, img: String?, sort: String?, type: Int, friend_type: Int) {
        val intent = Intent(context, ShareActivity::class.java)
        intent.putExtra("platform", platform)
        intent.putExtra("title", title)
        intent.putExtra("describe", describe)
        intent.putExtra("link", link)
        intent.putExtra("image", img)
        intent.putExtra("sort", sort)
        intent.putExtra("type", type)
        intent.putExtra("friend_type", friend_type)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        context.startActivity(intent)
    }

    fun doShare(context: Context, platform: String, title: String, describe: String, link: String, img: String, type: Int) {
        doShare(context, platform, title, describe, link, img, "321", type, 1)
    }

}
