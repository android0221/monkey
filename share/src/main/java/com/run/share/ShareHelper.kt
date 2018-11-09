package com.run.share


import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.run.common.BaseApplication
import com.run.common.base.BaseObserver
import com.run.common.utils.ULog
import com.run.common.view.MyBottomSheetDialog
import com.run.config.AppIntentAction
import com.run.config.modle.BaseModle
import com.run.login.api.LoginManager
import com.run.presenter.LoginHelper
import com.run.presenter.modle.login.ShareModle
import com.run.presenter.modle.login.ShareOpenModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import android.content.pm.PackageInfo
import android.content.pm.PackageManager


@Suppress("DEPRECATION")
class ShareHelper private constructor() {

    private var mContext: Context? = BaseApplication.context

    /**
     * 打开分享弹窗
     */
    fun doShare(context: Context?, articleid: Int, msg: String?) {
        if (context == null) return
        mContext = context
        showDialog(context, articleid, msg)
    }

    private fun showDialog(context: Context?, articleid: Int, msg: String?) {
        if (context == null) return
        mContext = context

        LoginManager.share_record_button().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<ShareOpenModle>() {
                    override fun onSuccess(o: ShareOpenModle) {
                        ULog.d("VersionData :" + o.data)
                        if (o.data == 0) {
                            exoDialog(context, articleid, msg, true)
                        } else {
                            exoDialog(context, articleid, msg, false)
                        }
                    }

                    override fun onError(errorType: Int, msg: String?) {
                        exoDialog(context, articleid, msg, false)
                        Toast.makeText(context, msg!!, Toast.LENGTH_SHORT).show()
                    }
                })

    }

    fun exoDialog(context: Context?, articleid: Int, msg: String?, open: Boolean) {
        val dialog = MyBottomSheetDialog(mContext!!)
        val view = View.inflate(mContext, R.layout.dialog_share_layout, null)
        val tv_msg: TextView = view.findViewById(R.id.tv_msg)
        tv_msg.text = "+" + msg + "元/位"
        dialog.setContentView(view)

        if (open) {
            view.findViewById<View>(R.id.ll_share_code).visibility = View.VISIBLE
        }
        view.findViewById<View>(R.id.tv_cancle).setOnClickListener { dialog.cancel() }
        view.findViewById<View>(R.id.ll_share_wc).setOnClickListener {
            //微信分享
            requestShare(context, 1, articleid)
            dialog.cancel()
        }
        view.findViewById<View>(R.id.ll_share_wc_friend).setOnClickListener {
            //朋友圈分享
            requestShare(context, 2, articleid)
            dialog.cancel()
        }
        view.findViewById<View>(R.id.ll_share_code).setOnClickListener {
            //复制链接
            requestShare(context, 3, articleid)
            dialog.cancel()
        }
        view.findViewById<View>(R.id.tv_shouming).setOnClickListener {
            //计费说明
            if (LoginHelper.instance.isLogin(mContext!!)) {
                AppIntentAction.jumpToShareDetailActivity(mContext!!)
            }
        }
        dialog.show()
    }

    fun requestShare(context: Context?, type: Int, articleid: Int) {
        if (mContext == null) {
            mContext = context
        }
        if (LoginHelper.instance.isLogin(context!!)) {
            LoginManager.shareRecord(articleid, type).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<ShareModle>() {
                        override fun onError(errorType: Int, msg: String?) {
                            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                        }

                        override fun onSuccess(o: ShareModle) {
                            exShare(o.share_data, type)
                        }
                    })
        }
    }


    /**
     * {"status":200,
     * "share_data":
     * {"url":"http:\/\/Tuf.stando.cn\/accustom-secretary-ribbon-DWpXY1JlB2UDZFU2V2BXLFA1UilWZg.do",
     * "friend_url":"http:\/\/W.xuangame.cn\/lump-find-customary-DWpXY1JlB2UDZFU2V2BXLFA1UilWZg.html",
     * "friend_type":1,
     * "sort":"321",
     * "title":"狐臭10年被逼疯，看她如何神奇逆转，香气逼人",
     * "share_picture":"http:\/\/wx1.sinaimg.cn\/mw690\/bbb6c919gy1fsniu5c620j205k046glj.jpg",
     * "content_describe":"您的好友最近都在看🔥"}}
     */
    /**
     * 执行分享操作
     */
    private fun exShare(shareBean: ShareModle.ShareDataBean?, type: Int) {
        if (mContext == null) {
            mContext = BaseApplication.context
        }
        if (shareBean == null) return
        var platform = "wechat_friend"
        var url = shareBean.url
        if (type == 3) {
            //复制链接
//            val cm = mContext!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//            cm.text = shareBean.content_describe
//            Toast.makeText(mContext, "链接复制成功!", Toast.LENGTH_SHORT).show()

            shareText(mContext!!, shareBean.title + "\n" + shareBean.url)
            return
        } else if (type == 2 || type == 4) {
            platform = "wechat_moments"
            url = shareBean.friend_url
        }
        if (type == 4) {
            UShare.doShare(mContext!!, platform, shareBean.title, shareBean.content_describe, url!!, shareBean.share_picture, shareBean.sort, 0, 2)
        } else {
            UShare.doShare(mContext!!, platform, shareBean.title, shareBean.content_describe, url!!, shareBean.share_picture, shareBean.sort, 0, shareBean.friend_type)
        }
    }

    /**
     * 分享多篇
     */
    private fun shareText(context: Context, msg: String) {
        if (isWeixinAvilible(context)) {//有微信分享到微信
            //判断是否安装了微信
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, msg)
            intent.type = "text/plain"
            intent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI")//微信朋友
            context.startActivity(intent)
        } else {//没有微信选择复制
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND;
            sendIntent.putExtra(Intent.EXTRA_TEXT, "分享文章到 \n$msg")//注意：这里只是分享文本内容
            sendIntent.type = "text/plain"
            context.startActivity(sendIntent)
        }
    }

    fun isWeixinAvilible(context: Context): Boolean {
        val packageManager = context.packageManager// 获取packagemanager
        val pinfo = packageManager.getInstalledPackages(0)// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == "com.tencent.mm") {
                    return true
                }
            }
        }
        return false
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var shareHelper: ShareHelper? = null
        val instance: ShareHelper
            get() {
                if (shareHelper == null) {
                    synchronized(LoginHelper::class.java) {
                        if (shareHelper == null) {
                            shareHelper = ShareHelper()
                        }
                    }
                }
                return this.shareHelper!!
            }
    }
}
