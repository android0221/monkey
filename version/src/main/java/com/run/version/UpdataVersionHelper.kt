package com.run.version


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.run.common.base.BaseObserver
import com.run.common.dialog.DialogHelper
import com.run.common.utils.*
import com.run.login.api.LoginManager
import com.run.login.modle.VersionModle
import com.run.presenter.modle.login.TitleModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import zlc.season.rxdownload2.RxDownload
import zlc.season.rxdownload2.entity.DownloadStatus
import java.io.File


/**
 * 版本跟新工具类
 */
class UpdataVersionHelper private constructor() {
    private val TAG = "UpdataVersionHelper"
    private var mContext: Activity? = null

    /**
     * 开始下载
     */
    private var download: RxDownload? = null
    private var disposable: Disposable? = null

    private var progressView: View? = null
    private var tv_progress: TextView? = null
    private var tv_tottal: TextView? = null
    private var progressBar: ProgressBar? = null
    /**
     * 检查版本更新
     */
    fun checkUpadata(context: Activity?, type: Int) {
        if (context == null) return
        this.mContext = context
        LoginManager.versions().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<VersionModle>() {
                    override fun onSuccess(versionJsonBean: VersionModle) {
                        if (versionJsonBean == null || versionJsonBean.status == -100)
                            return
                        if (versionJsonBean.status == 200) {
                            if (versionJsonBean.versions > UVersion.getLocalVersion(mContext!!)) {
                                showUpdataDialog(versionJsonBean.type, versionJsonBean.describe, versionJsonBean.url)
                            } else {
                                if (type == 0) {
                                    showNoUpadataMsg("已经是最新版本")
                                } else {
                                    getActivity()
                                }
                            }
                        }
                    }

                    override fun onError(errorType: Int, msg: String?) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun getActivity() {
        LoginManager.activity().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<TitleModle>() {
                    override fun onSuccess(titleModle: TitleModle) {
                        showHintDialog(titleModle.title, titleModle.content)
                    }

                    override fun onError(errorType: Int, msg: String?) {

                    }
                })
    }


    /**
     * 显示公告对话框 ,12个小时内只能显示一次 
     */
    private fun showHintDialog(title: String?, msg: String?) {
        var time = System.currentTimeMillis()
        var lastTime: Long = USharePreference.get(mContext!!, "TIME", 0.toLong()) as Long
        ULog.e(TAG, "当前时间：" + time + ",上次时间：" + lastTime + "时间差：" + (time - lastTime))
        if (time - lastTime < 12 * 60 * 60 * 1000) return
        USharePreference.put(mContext!!, "TIME", time)


        if (TextUtils.isEmpty(msg) || mContext == null) return
        try {
            val contentView = View.inflate(mContext, R.layout.dialog_msg_hint_layout, null)
            val wb_msg: WebView = contentView.findViewById(R.id.wb_msg)
            wb_msg.setBackgroundColor(0) // 设置背景色
            wb_msg.background.alpha = 0 // 设置填充透明度 范围：0-255
            // msg = msg.replace("#227700", "#ffffff");
            wb_msg.loadDataWithBaseURL(null, UWebView.getContent(msg!!), "text/html", "utf-8", null)
            DialogHelper.showDialog(mContext!!, contentView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 显示已经市最新版本了
     */
    private fun showNoUpadataMsg(msg: String) {
        if (TextUtils.isEmpty(msg) || mContext == null) return
        val contentView = View.inflate(mContext, R.layout.dialog_version_updata_no_layout, null)
        contentView.findViewById<View>(R.id.tv_cancle).setOnClickListener { DialogHelper.closeDialog() }
        DialogHelper.showDialog(mContext!!, contentView)
    }

    /**
     * 显示跟新
     */
    private fun showUpdataDialog(type: Int, describe: String?, url: String?) {
        if (mContext == null) return
        var contentView: View? = null
        when (type) {
            0//立即更新
            -> {
                contentView = View.inflate(mContext, R.layout.dialog_version_updata_layout, null)
                val tv_msg = contentView!!.findViewById<TextView>(R.id.tv_msg)
                tv_msg.text = describe
                contentView.findViewById<View>(R.id.tv_cancle).setOnClickListener { DialogHelper.closeDialog() }
                contentView.findViewById<View>(R.id.tv_ok).setOnClickListener {
                    downloadApk(url, 0)
                    DialogHelper.closeDialog()
                }
                DialogHelper.showDialog(mContext!!, contentView)
            }
            1//强制更新
            -> {
                contentView = View.inflate(mContext, R.layout.dialog_version_updata_layout2, null)
                val tv_msg2 = contentView!!.findViewById<TextView>(R.id.tv_msg)
                tv_msg2.text = describe
                contentView.findViewById<View>(R.id.tv_cancle).setOnClickListener {
                    DialogHelper.closeDialog()
                    UActivityManager.exit()
                }
                contentView.findViewById<View>(R.id.tv_ok).setOnClickListener {
                    downloadApk(url, 1)
                    DialogHelper.closeDialog()
                }
                DialogHelper.showDialog(mContext!!, contentView, false)
            }
        }
    }

    private fun downloadApk(url: String?, type: Int) {
        if (download == null) {
            download = RxDownload.getInstance(mContext)
        }
        disposable = download!!.download(url)                       //只传url即可
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ status ->
                    ULog.e("正在下载：" + status.percent)
                    //显示下载进度
                    showUpdataDownloadDialog(status, type)
                }, { throwable ->
                    //下载失败
                    DialogHelper.closeDialog()
                    ULog.e("下载失败：$throwable")
                }, {
                    ULog.e("下载成功：")
                    DialogHelper.closeDialog()
                    //下载成功
                    val files = download!!.getRealFiles(url)
                    installApk(mContext, files!![0], type)
                })
    }


    /**
     * @param status
     * @param type
     */
    private fun showUpdataDownloadDialog(status: DownloadStatus?, type: Int) {
        if (status == null || mContext == null) return
        if (progressView == null) {
            progressView = View.inflate(mContext, R.layout.dialog_version_updata_progress_layout, null)
            progressBar = progressView!!.findViewById(R.id.progressBar)
            tv_progress = progressView!!.findViewById(R.id.tv_progress)
            tv_tottal = progressView!!.findViewById(R.id.tv_tottal)
            if (type == 1) {
                progressView!!.findViewById<View>(R.id.tv_cancle).setVisibility(View.GONE)
            } else {
                progressView!!.findViewById<View>(R.id.tv_cancle).setOnClickListener {
                    DialogHelper.closeDialog()
                    if (disposable != null) {
                        disposable!!.dispose()
                        disposable = null
                    }
                }
            }
        }
        progressBar!!.progress = status.percentNumber.toInt()
        tv_progress!!.text = "下载中" + status.percent
        tv_tottal!!.text = status.formatDownloadSize + "/" + status.formatTotalSize
        if (type == 1) {
            DialogHelper.showDialog(mContext!!, progressView, false)
        } else {
            DialogHelper.showDialog(mContext!!, progressView)
        }
    }

    /**
     * 安装APK
     */
    fun installApk(context: Context?, file: File?, type: Int) {
        ULog.d("安装文件")
        if (file == null) return
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Intent.ACTION_VIEW
        val uri: Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = VersionFileProvider.getUriForFile(context!!, "com.yun.updata.pepper.versionProvider", file)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            uri = Uri.fromFile(file)
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        context!!.startActivity(intent)
        if (type == 1) {
            UActivityManager.exit()
        }
    }

    companion object {
        private var instance: UpdataVersionHelper? = null
        fun getInstance(): UpdataVersionHelper {
            if (instance == null) {
                synchronized(UpdataVersionHelper::class.java) {
                    if (instance == null) {
                        instance = UpdataVersionHelper()
                    }
                }
            }
            return instance!!
        }
    }
}