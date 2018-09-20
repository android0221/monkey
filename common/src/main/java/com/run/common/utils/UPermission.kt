package com.run.common.utils

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity


/**
 * 权限检查
 *
 * 需要在请求的Activity中重写onRequestPermissionsResult()方法
 */
object UPermission {
    private val TAG = UPermission.javaClass.name
    private const val PERMISSION_OPER_EXTERNAL_STORAGE = 55

    /**
     * 检查是否有该权限
     */
    fun checkPermission(activity: AppCompatActivity, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermission(activity, PERMISSION_OPER_EXTERNAL_STORAGE, *permissions)
                return false
            }
        }
        return true
    }


    /**
     * 请求权限
     */
    private fun requestPermission(act: AppCompatActivity, code: Int, vararg permissions: String) {
        ULog.d(TAG, "请求相应的权限" + permissions.toString())
        ActivityCompat.requestPermissions(act, permissions, code)
    }


    /**
     * 权限请求回调
     */
    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray): Boolean {
        if (requestCode == PERMISSION_OPER_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty()) {
                for (permissionStatus: Int in grantResults) {
                    if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                        ULog.d(TAG, "权限被拒绝")
                        return false
                    }
                }
                ULog.d(TAG, "权限通过")
                return true
            }
        }
        ULog.d(TAG, "没有相应的权限放回")
        return false
    }

}