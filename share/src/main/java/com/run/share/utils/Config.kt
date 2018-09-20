package com.run.share.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import java.util.*

/**
 * @author chenqiang
 */
object Config {

    val INFOS: LinkedHashMap<String, String>?
    lateinit var sharePkg: String
    lateinit var shareAppId: String

    init {
        INFOS = LinkedHashMap()
    }



    fun checkIfNoneShowIntall(context: Context, sort: String) {
        if (INFOS != null && INFOS.size > 0) {
            INFOS.clear()
        }
        if (TextUtils.isEmpty(sort)) {
            INFOS!!["com.UCMobile"] = "wx020a535dccd46c11"
            INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
            INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
        } else if (sort == "123") {
            INFOS!!["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
            INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
            INFOS["com.UCMobile"] = "wx020a535dccd46c11"
        } else if (sort == "132") {
            INFOS!!["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
            INFOS["com.UCMobile"] = "wx020a535dccd46c11"
            INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
        } else if (sort == "213") {
            INFOS!!["com.tencent.mtt"] = "wx64f9cf5b17af074d"
            INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
            INFOS["com.UCMobile"] = "wx020a535dccd46c11"

        } else if (sort == "231") {
            INFOS!!["com.tencent.mtt"] = "wx64f9cf5b17af074d"
            INFOS["com.UCMobile"] = "wx020a535dccd46c11"
            INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"

        } else if (sort == "312") {
            INFOS!!["com.UCMobile"] = "wx020a535dccd46c11"
            INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
            INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
        } else if (sort == "321") {
            INFOS!!["com.UCMobile"] = "wx020a535dccd46c11"
            INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
            INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
        } else if (sort == "4") {
            INFOS!!["com.UCMobile"] = "wx020a535dccd46c11"
            INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
            INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
        } else {
            INFOS!!["com.UCMobile"] = "wx020a535dccd46c11"
            INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
            INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
        }
        var i = 0
        for ((key, value) in INFOS) {
            try {
                context.packageManager.getPackageInfo(key, 0)
                sharePkg = key
                shareAppId = value
                return
            } catch (e: PackageManager.NameNotFoundException) {
                if (i == INFOS.size - 1) {
                    sharePkg = ""
                    shareAppId = ""
                    showInstallDialog(context)
                }
            }

            i++
        }
    }

    private fun showInstallDialog(context: Context) {
        val appIndex = intArrayOf(0)
        val builder = AlertDialog.Builder(context)
        builder.setTitle("安装其中一款后即可快速分享(只需安装),您也可以自己去应用商店下一款")
        builder.setSingleChoiceItems(arrayOf("QQ(完整版)-推荐", "QQ浏览器", "UC浏览器"), 0) { dialog, which -> appIndex[0] = which }
        builder.setNegativeButton("放弃分享") { dialog, which ->
            if (context is Activity) {
                context.finish()
            }
        }
        builder.setPositiveButton("立即安装") { dialog, which ->
            var uri = ""
            if (appIndex[0] == 0) {
                uri = "http://wap.uc.cn/packinfo/chinese_999/ucbrowser/pf/145?uc_param_str=vepffrbiupladsdnni&r=main&from=wap-atp-mobile&plang="
            } else if (appIndex[0] == 1) {
                uri = "http://mdc.html5.qq.com/?channel_id=22579"
            } else if (appIndex[0] == 2) {
                uri = "http://im.qq.com/download/"
            }
            context.startActivity(Intent("android.intent.action.VIEW", Uri.parse(uri)))
            dialog.dismiss()
            if (context is Activity) {
                context.finish()
            }
        }
        builder.setCancelable(false)
        builder.show()
    }
}
