@file:Suppress("DEPRECATION")

package com.run.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object UNetwork {

    val TYPE_NONE = -1 //没有网络
    val TYPE_MOBILE = 0 //手机网络
    val TYPE_WIFI = 1  //WiFI网络

    /**
     * 获取网络状态
     *
     * @param context
     * @return one of TYPE_NONE, TYPE_MOBILE, TYPE_WIFI
     * @permission android.permission.ACCESS_NETWORK_STATE
     */
    fun getNetWorkStates(context: Context): Int {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected) {
            return TYPE_NONE//没网
        }

        val type = activeNetworkInfo.type
        when (type) {
            ConnectivityManager.TYPE_MOBILE -> return TYPE_MOBILE//移动数据
            ConnectivityManager.TYPE_WIFI -> return TYPE_WIFI//WIFI
        }
        return TYPE_NONE
    }

    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context
                .applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager ?: return false
        val networkinfo = manager.activeNetworkInfo

        return !(networkinfo == null || !networkinfo.isAvailable)

    }


}
