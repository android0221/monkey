package com.run.ui.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import android.telephony.SmsManager
import com.run.common.base.BaseObserver
import com.run.common.utils.ULog
import com.run.common.utils.UNetwork
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.ArticleTypeModle
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.*

@Suppress("UNREACHABLE_CODE")
class MyService : Service() {

    companion object {
        fun openMyService(context: Context) {
            val intent = Intent(context, MyService::class.java)
            context.startService(intent)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ULog.d("xiaoruan", "onStartCommand()" + flags + "startid :" + startId)
        startTimer(10)
        return super.onStartCommand(intent, flags, startId)

    }

    private var errorCount: Int = 0
    @SuppressLint("CheckResult")
    private fun startTimer(time: Long) {
        Observable.timer(time, SECONDS).subscribeOn(Schedulers.io()).subscribe {
            if (!UNetwork.isNetworkAvailable(this)) {
                ULog.d("MyService", "暂无可用网络")
                startTimer(5 * 60)
                return@subscribe
            }
            ApiManager.article().subscribeOn(Schedulers.io()).subscribe(object : BaseObserver<ArticleTypeModle>() {
                override fun onSuccess(o: ArticleTypeModle) {
                    ULog.d("MyService", "网络监测成功！" + System.currentTimeMillis())
                    errorCount = 0
                    startTimer(5 * 60)
                }

                override fun onError(errorType: Int, msg: String?) {
                    errorCount++
                    when (errorCount) {
                        1, 2, 3 -> {
                            ULog.d("MyService", "网络监测异常第" + errorCount + "次")
                            startTimer(30)
                        }
                        4 -> {
                            //播放音乐
                            defaultCallMediaPlayer()
                            startTimer(60)
                        }
                        5 -> {
                            ULog.d("MyService", "网络监测异常第" + errorCount + "次")
                            startTimer(30)
                            ULog.d("MyService: 发消息")
                            sendMsg("13077894546", "红荔枝APP获取数据失败，请检查服务器网络")
                        }
                        6, 7, 8, 9, 10 -> {
                            ULog.d("MyService", "网络监测异常第" + errorCount + "次")
                            ULog.d("MyService: 打电话")
                            call("13077894546")
                            startTimer(10 * 60)
                        }
                    }
                }
            })
        }
    }


    /**
     * 发送信息
     */
    val SENT_SMS_ACTION = "SENT_SMS_ACTION"

    fun sendMsg(tel: String, content: String) {
        val sendIntent = Intent(SENT_SMS_ACTION)
        val sendPI = PendingIntent.getBroadcast(this, 0, sendIntent, 0)
        val smsManager = SmsManager.getDefault()
        val divideContents = smsManager.divideMessage(content)
        for (text in divideContents) {
            smsManager.sendTextMessage(tel, null, text, sendPI, null)
        }
    }

    @SuppressLint("MissingPermission")
            /**
             * 打电话
             */
    fun call(phoneNum: String) {
        val intent = Intent(Intent.ACTION_CALL)
        val data = Uri.parse("tel:$phoneNum")
        intent.data = data
        startActivity(intent)
    }


    /**
     * 播放系统铃声
     */
    fun defaultCallMediaPlayer() {
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val r: Ringtone = RingtoneManager.getRingtone(this, notification)
        r.play()
    }

    override fun onCreate() {
        super.onCreate()
        ULog.d("xiaoruan", "onCreate()")
    }

    override fun onDestroy() {
        super.onDestroy()
        ULog.d("xiaoruan", "onDestroy()")
    }
}
