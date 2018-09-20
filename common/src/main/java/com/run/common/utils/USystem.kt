@file:Suppress("DEPRECATION")

package com.run.common.utils


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import android.webkit.WebSettings


/**
 * 获取数据信息
 */
object USystem {

    /**
     * 获取手机型号的IMEI 需要android.permission.READ_PHONE_STATE
     */
    @SuppressLint("MissingPermission", "WrongConstant", "HardwareIds")
    fun getIMEI(context: Context): String {

        var imei = ""
        try {
            val telephonyManager = context.getSystemService("phone") as TelephonyManager
            imei = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                telephonyManager.imei
            } else {
                telephonyManager.deviceId
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return imei
    }

    /**
     * 获取UserAgent
     */
    fun getUserAgent(context: Context): String {
        var userAgent: String
        var sb: StringBuffer? = null
        try {
            userAgent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    WebSettings.getDefaultUserAgent(context)
                } catch (e: Exception) {
                    System.getProperty("http.agent")
                }

            } else {
                System.getProperty("http.agent")
            }
            sb = StringBuffer()
            var i = 0
            val length = userAgent.length
            while (i < length) {
                val c = userAgent[i]
                if (c <= '\u001f' || c >= '\u007f') {
                    sb.append(String.format("\\u%04x", c.toInt()))
                } else {
                    sb.append(c)
                }
                i++
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return sb!!.toString()
    }

}
