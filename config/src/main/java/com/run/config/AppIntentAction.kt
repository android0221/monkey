@file:Suppress("DEPRECATION")

package com.run.config

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.widget.Toast


/**
 * App跳转使用的Action
 */
object AppIntentAction {

    private const val TAG = "AppIntentAction"


    fun jumpToShareDetailActivity(act: Context) {
        val intent = Intent()
        intent.action = "com.action.monkey.ContentDetailActivity"
        intent.addCategory("com.action.category")
        intent.putExtra("TITLE", "计费规则说明")
        intent.putExtra("id", 5)
        act.startActivity(intent)
    }

    //=============================加入qq群========================================
    /**
     * 加入QQ群
     */
    fun joinQQGroup(qqKey: String?, context: Context): Boolean {
        if (TextUtils.isEmpty(qqKey)) {
            Toast.makeText(context, "请稍后联系！", Toast.LENGTH_SHORT).show()
            return false
        }
        val intent = Intent()
//        var qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + AppConstants.QQKey
        var qunUrl = "mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D$qqKey"
        intent.data = Uri.parse(qunUrl)
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return try {
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            // 未安装手Q或安装的版本不支持
            Toast.makeText(context, "未安装手Q或安装的版本不支持！", Toast.LENGTH_SHORT).show()
            false
        }
    }
}
