package com.run.presenter

import android.content.Context
import android.content.Intent
import android.text.TextUtils

import com.run.common.BaseApplication
import com.run.common.utils.USharePreference

class LoginHelper private constructor() {

    private var mMobile: String? = null
    private var mPassword: String? = null
    private var mToken: String? = null

    /**
     * 检查是否是否登录
     */
    val isLogin: Boolean
        get() = !TextUtils.isEmpty(getmToken())

    private var mContext: Context? = null

    fun setmToken(mToken: String) {
        this.mToken = mToken
        //清空Token
        USharePreference.put(BaseApplication.context!!, "TOKEN", mToken)
    }

    fun getmMobile(): String? {
        if (TextUtils.isEmpty(mMobile)) {
            mMobile = USharePreference[BaseApplication.context!!, "MOBILE", ""] as String?
        }
        return mMobile
    }

    fun getmPassword(): String? {
        if (TextUtils.isEmpty(mPassword)) {
            mPassword = USharePreference[BaseApplication.context!!, "PASSWORD", ""] as String?
        }
        return mPassword
    }

    fun getmToken(): String? {
        if (TextUtils.isEmpty(mToken)) {
            mToken = USharePreference[BaseApplication.context!!, "TOKEN", ""] as String?
        }
        return mToken
    }

    /**
     * 保存登陆信息
     */
    fun save(mobile: String, password: String, token: String) {
        USharePreference.put(BaseApplication.context!!, "MOBILE", mobile)
        USharePreference.put(BaseApplication.context!!, "PASSWORD", password)
        if (TextUtils.isEmpty(token)) { return }
        USharePreference.put(BaseApplication.context!!, "TOKEN", token)
    }



     fun isLogin(context: Context): Boolean {
        val isLogin = !TextUtils.isEmpty(getmToken())
        if (!isLogin) { showLogin(context) }
        return isLogin
    }

    /**
     * 打开登录弹窗
     */
    fun showLogin(context: Context?) {
        if (context == null) return
        this.mContext = context

        val intent = Intent()
        intent.action = "com.run.lychee.login.LoginActivity"
        context.startActivity(intent)
    }

    companion object {
        private val TAG: String = LoginHelper.javaClass.name
        private var loginHelper: LoginHelper? = null
        val instance: LoginHelper
            get() {
                if (loginHelper == null) {
                    synchronized(LoginHelper::class.java) {
                        if (loginHelper == null) {
                            loginHelper = LoginHelper()
                        }
                    }
                }
                return loginHelper!!
            }
    }


}
