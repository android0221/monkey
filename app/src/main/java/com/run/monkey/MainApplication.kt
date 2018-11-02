package com.run.monkey

import android.os.Build
import android.os.StrictMode
import com.run.common.BaseApplication
import com.run.common.utils.ULog
import com.tencent.bugly.crashreport.CrashReport
import com.umeng.socialize.Config
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI

class MainApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        initShare()
        ULog.init(true)
        initCrashReport()
    }

    private fun initCrashReport() {
        //红荔枝 208c16ede9
        CrashReport.initCrashReport(applicationContext, "208c16ede9", false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
        }
    }


    /**
     * 初始化分享插件
     */
    private fun initShare() {
        PlatformConfig.setWeixin("wx07f7a310c8bb9537",
                "aa798e4fc45a0dfa878500492a7ef656")
        UMShareAPI.get(this)
        Config.DEBUG = false
    }

}