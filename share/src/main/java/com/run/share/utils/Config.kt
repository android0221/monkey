package com.run.share.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import com.run.common.utils.ULog
import com.run.share.modle.WCShareBean
import java.util.*
import kotlin.collections.ArrayList

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


    fun checkIfNoneShowIntall(context: Context, sort: String?) {
        if ("4321".equals(sort)) {
            checkIfNoneShowIntall2(context)
            return
        }

        if (INFOS != null && INFOS.size > 0) {
            INFOS.clear()
        }
        when {
            TextUtils.isEmpty(sort) -> {
                INFOS!!["com.baidu.BaiduMap"] = "wx9a08a4f59ce91bf6"
                INFOS!!["com.UCMobile"] = "wx020a535dccd46c11"
                INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
                INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
            }
            sort == "123" -> {
                INFOS!!["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
                INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
                INFOS["com.UCMobile"] = "wx020a535dccd46c11"
            }
            sort == "132" -> {
                INFOS!!["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
                INFOS["com.UCMobile"] = "wx020a535dccd46c11"
                INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
            }
            sort == "213" -> {

                INFOS!!["com.tencent.mtt"] = "wx64f9cf5b17af074d"
                INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
                INFOS["com.UCMobile"] = "wx020a535dccd46c11"
            }
            sort == "231" -> {
                INFOS!!["com.tencent.mtt"] = "wx64f9cf5b17af074d"
                INFOS["com.UCMobile"] = "wx020a535dccd46c11"
                INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
            }
            sort == "312" -> {
                INFOS!!["com.UCMobile"] = "wx020a535dccd46c11"
                INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
                INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
            }
            sort == "321" -> {
                INFOS!!["com.UCMobile"] = "wx020a535dccd46c11"
                INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
                INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
            }
            sort == "4321" -> {
                INFOS!!["com.netease.cloudmusic"] = "wx8dd6ecd81906fd84"  //网易云音乐
                INFOS["com.smile.gifmaker"] = "wxaadbab9d13edff20"  //快手
                INFOS["com.sina.weibo"] = "wx299208e619de7026"  //微博
                INFOS["com.tencent.ttpic"] = "wx6ed88e3698dd4318"  //天天p图
                INFOS["com.ximalaya.ting.android"] = "wxb9371ecb5f0f05b1"  //喜马拉雅
                INFOS["com.tencent.karaoke"] = "wx2ed190385c3bafeb"  //全民K歌
                INFOS["com.snda.wifilocating"] = "wx13f22259f9bbd047"  //WIFI钥匙
                INFOS["com.immomo.momo"] = "wx53440afb924e0ace"  //陌陌
                INFOS["com.tencent.weishi"] = "wx5dfbe0a95623607b"  //微视
                INFOS["com.tencent.news"] = "wx073f4a4daff0abe8"  //腾讯新闻
                INFOS["com.moji.mjweather"] = "wx300c410f4257c6f3"  //墨迹天气
                INFOS["com.baidu.searchbox"] = "wx27a43222a6bf2931"  //百度
                INFOS["com.sankuai.meituan"] = "wxa552e31d6839de85"  //美团
                INFOS["com.jingdong.app.mall"] = "wxe75a2e68877315fb"  //京东
                INFOS["com.xunmeng.pinduoduo"] = "wx77d53b84434b9d9a"  //拼多多
                INFOS["com.baidu.BaiduMap"] = "wx9a08a4f59ce91bf6"//百度地图
                INFOS["com.UCMobile"] = "wx020a535dccd46c11"//UC浏览器
                INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"//qq浏览器
                INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"//QQ
            }

            else -> {
                INFOS!!["com.xunmeng.pinduoduo"] = "wx77d53b84434b9d9a"  //拼多多
                INFOS["com.UCMobile"] = "wx020a535dccd46c11"
                INFOS["com.tencent.mtt"] = "wx64f9cf5b17af074d"
                INFOS["com.tencent.mobileqq"] = "wxf0a80d0ac2e82aa7"
            }
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


    var allShareList: ArrayList<WCShareBean> = arrayListOf()
    var wcShareList: ArrayList<WCShareBean> = arrayListOf()
    fun checkIfNoneShowIntall2(context: Context) {
        if (allShareList.size <= 0) {
            allShareList.add(WCShareBean("com.netease.cloudmusic", "wx8dd6ecd81906fd84"))//网易云音乐
            allShareList.add(WCShareBean("com.smile.gifmaker", "wxaadbab9d13edff20")) //快手
            allShareList.add(WCShareBean("com.sina.weibo", "wx299208e619de7026")) //微博
            allShareList.add(WCShareBean("com.tencent.ttpic", "wx6ed88e3698dd4318")) //天天p图
            allShareList.add(WCShareBean("com.ximalaya.ting.android", "wxb9371ecb5f0f05b1")) //喜马拉雅
            allShareList.add(WCShareBean("com.tencent.karaoke", "wx2ed190385c3bafeb")) //全民K歌
            allShareList.add(WCShareBean("com.snda.wifilocating", "wx13f22259f9bbd047")) //WIFI钥匙
            allShareList.add(WCShareBean("com.immomo.momo", "wx53440afb924e0ace")) //陌陌
            allShareList.add(WCShareBean("com.tencent.weishi", "wx5dfbe0a95623607b")) //微视
            allShareList.add(WCShareBean("com.tencent.news", "wx073f4a4daff0abe8")) //腾讯新闻
            allShareList.add(WCShareBean("com.moji.mjweather", "wx300c410f4257c6f3")) //墨迹天气
            allShareList.add(WCShareBean("com.baidu.searchbox", "wx27a43222a6bf2931")) //百度
            allShareList.add(WCShareBean("com.sankuai.meituan", "wxa552e31d6839de85")) //美团
            allShareList.add(WCShareBean("com.jingdong.app.mall", "wxe75a2e68877315fb")) //京东
            allShareList.add(WCShareBean("com.xunmeng.pinduoduo", "wx77d53b84434b9d9a")) //拼多多
            allShareList.add(WCShareBean("com.baidu.BaiduMap", "wx9a08a4f59ce91bf6")) //百度地图
            allShareList.add(WCShareBean("com.UCMobile", "wx020a535dccd46c11")) //UC浏览器
            allShareList.add(WCShareBean("com.tencent.mtt", "wx64f9cf5b17af074d")) //qq浏览器
            allShareList.add(WCShareBean("com.tencent.mobileqq", "wxf0a80d0ac2e82aa7")) //QQ
            allShareList.add(WCShareBean("com.ss.android.article.news", "wx50d801314d9eb858")) //今日头条
            allShareList.add(WCShareBean("com.tencent.android.qqdownloader", "wx3909f6add1206543")) //应用宝

            for (bean in allShareList) {
                if (isPageAvilible(context, bean.wc_key!!)) {
                    wcShareList.add(bean)
                }
            }
        }
        if (wcShareList.size <= 0) {
            sharePkg = ""
            shareAppId = ""
            showInstallDialog(context)
        }
        val index = Random().nextInt(wcShareList.size)
        ULog.d("xiaoruan", "index = $index")
        sharePkg = wcShareList[index].wc_key!!
        shareAppId = wcShareList[index].wc_value!!

    }


    fun isPageAvilible(context: Context, packagename: String): Boolean {
        val packageManager = context.packageManager// 获取packagemanager
        val pinfo = packageManager.getInstalledPackages(0)// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == packagename) {
                    return true
                }
            }
        }
        return false
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
            when {
                appIndex[0] == 0 -> uri = "http://wap.uc.cn/packinfo/chinese_999/ucbrowser/pf/145?uc_param_str=vepffrbiupladsdnni&r=main&from=wap-atp-mobile&plang="
                appIndex[0] == 1 -> uri = "http://mdc.html5.qq.com/?channel_id=22579"
                appIndex[0] == 2 -> uri = "http://im.qq.com/download/"
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
