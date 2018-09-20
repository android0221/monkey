package com.run.monkey

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.run.common.dialog.DialogCallBack
import com.run.common.dialog.PermissionDialog
import com.run.common.helper.SharedPreferenceHelper
import com.run.common.utils.ULog
import com.run.common.utils.UPermission
import com.run.ui.activity.MainActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

@Suppress("JAVA_CLASS_ON_COMPANION")
/**
 * 闪屏启动页面
 */
class SplashActivity : AppCompatActivity() {

    companion object {
        val TAG: String = SplashActivity.javaClass.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initAnim()
    }

    /**
     * 初始化动画
     */
    private fun initAnim() {
//        ULog.d(TAG, "初始化动画")
//        mSplashAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
//            override fun onAnimationRepeat(animation: Animator?) {}
//            override fun onAnimationEnd(animation: Animator?) {
//                ULog.d(TAG, "结束动画")
//                if (SharedPreferenceHelper.checkHasOpenGuide(this@SplashActivity)) {
//                    MainActivity.newInstance(this@SplashActivity)
//                } else {
//                    GuideActivity.newInstance(this@SplashActivity)
//                }
//            }
//            override fun onAnimationCancel(animation: Animator?) {
//                ULog.d(TAG, "取消动画")
//            }
//            override fun onAnimationStart(animation: Animator?) {
//                ULog.d(TAG, "开启动画")
//            }
//        })


    }

    override fun onStart() {
        super.onStart()
        //如果权限对话框打开，则不检查权限
        if (dialogShow) return
        checkPermission()
    }
    //==========================================权限检查==================================================================================
    /**
     * 需要初始化的权限
     */
    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)

    /**
     * 检查权限
     */
    private fun checkPermission() {
        ULog.d(TAG, "开始检查权限")
        if (UPermission.checkPermission(this, *permissions)) {
            //通过权限
            permissionSuccess()
        }
    }

    /**
     * 权限回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        ULog.d(TAG, "权限请求开始回调")
        val permissionStatus: Boolean = UPermission.onRequestPermissionsResult(requestCode, grantResults)
        if (permissionStatus) {
            //权限通过
            permissionSuccess()
        } else {
            //没有通过权限请求
            requestPermissionDialog()
        }
    }

    private var dialogShow = false //对话框是否开启
    /**
     * 还有权限没有开启，用户再次确认
     */
    private fun requestPermissionDialog() {
        ULog.d(TAG, "权限没通过，请求用户再次确认")
        PermissionDialog.newInstance(this@SplashActivity).show(this@SplashActivity, callBack = object : DialogCallBack {
            override fun onNext() {
                ULog.d(TAG, "重新请求权限")
                checkPermission()
                dialogShow = false
            }

            override fun cancle() {
                finish()
                dialogShow = false
                ULog.d(TAG, "关闭应用")
            }
        })
        dialogShow = true
    }

    /**
     * 获取权限成功
     */
    private fun permissionSuccess() {
        ULog.d(TAG, "权限请求通过")
        //点击播放
//        mSplashAnimationView.playAnimation()
        Observable.timer(1, TimeUnit.SECONDS).subscribe {
            if (SharedPreferenceHelper.checkHasOpenGuide(this@SplashActivity)) {
                MainActivity.newInstance(this@SplashActivity)
            } else {
                GuideActivity.newInstance(this@SplashActivity)
            }
        }
    }
}
