package com.run.login.api


import com.run.common.BaseApplication
import com.run.common.utils.UEncrypt
import com.run.common.utils.URetrofit
import com.run.config.AppConstants
import com.run.config.modle.BaseModle
import com.run.login.modle.VersionModle
import com.run.presenter.LoginHelper
import com.run.presenter.modle.login.*
import io.reactivex.Observable
import org.json.JSONException
import org.json.JSONObject

object LoginManager {

    private var apiService: LoginService? = null//登录
    private val instance: LoginService
        get() {
            if (apiService == null) {
                synchronized(LoginService::class.java) {
                    if (apiService == null) {
                        apiService = URetrofit.getInstance(AppConstants.BASE_URL, BaseApplication.context!!)!!.create(LoginService::class.java)
                    }
                }
            }
            return this!!.apiService!!
        }

    /**
     * 登陆
     * @param mobile
     * @param password
     * @return
     */
    fun verificationLogin(mobile: String, password: String): Observable<LoginModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("mobile", mobile)
            jsonObject.put("password", password)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return LoginManager.instance.verificationLogin(UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 获取验证吗
     * @param mobile
     * @param type
     * @return
     */
    fun getCode(mobile: String, type: Int): Observable<BaseModle> {//type类型 1:注册 2：找回密码
        val jsonObject = JSONObject()
        try {
            jsonObject.put("mobile", mobile)
            jsonObject.put("type", type)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return LoginManager.instance.getCode(UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 用户注册
     * @param mobile
     * @param code
     * @param password
     * @return
     */
    fun verifyRegister(mobile: String, code: String, password: String): Observable<BaseModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("nick_name", "")
            jsonObject.put("mobile", mobile)
            jsonObject.put("verification_code", code)
            jsonObject.put("password", password)
            jsonObject.put("confirm_password", password)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return LoginManager.instance.verifyRegister(UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }
    /**
     * 重置密码
     * @param mobile
     * @param code
     * @param password
     * @return
     */
    fun retrievePassword(mobile: String, code: String, password: String): Observable<BaseModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("mobile", mobile)
            jsonObject.put("verification_code", code)
            jsonObject.put("password", password)
            jsonObject.put("confirm_password", password)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.retrievePassword(UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 退出登录
     *
     * @return
     */
    fun logout(): Observable<BaseModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.logout(LoginHelper.instance.getmMobile()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 获取qq的key
     *
     * @return
     */
    fun getQQKey(): Observable<QQModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.getQQKey(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 意见反馈
     *
     * @param title
     * @param content
     * @param phone
     * @return
     */
    fun feedback(title: String, content: String, phone: String): Observable<BaseModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("title", title)
            jsonObject.put("content", content)
            jsonObject.put("phone", phone)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.feedback(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 新手指导列表
     */
    fun dynamics(type: Int): Observable<DynamicsModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("type", type)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.dynamics(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 新手指导内容
     */
    fun problemContent(z_id: Int): Observable<DyContentModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("z_id", z_id)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.problemContent(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 签到
     *
     * @return
     */
    fun sign(): Observable<BaseModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.sign(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 首页弹窗
     */
    fun activity(): Observable<TitleModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.activity(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 用户信息详情
     */
    fun userInfo(): Observable<UserInfoModile> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.userInfo(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 拜师
     *
     * @return
     */
    fun boundTeacher(first_id: String): Observable<BaseModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("first_id", first_id)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.boundTeacher(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    //=====================================================================分享=======================================================================

    /**
     * 获取分享内容
     *
     * @param article_id
     * @param share_type
     * @return
     */
    fun shareRecord(article_id: Int, share_type: Int): Observable<ShareModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("article_id", article_id)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
            jsonObject.put("share_type", share_type)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.shareRecord(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    //=============================================================版本更新=================================

    /**
     * 版本更新
     */
    fun versions(): Observable<VersionModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return LoginManager.instance.versions(LoginHelper.instance.getmToken()!!, UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }
}
