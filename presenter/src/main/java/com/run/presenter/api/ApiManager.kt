package com.run.presenter.api


import com.run.common.BaseApplication
import com.run.common.utils.UEncrypt
import com.run.common.utils.URetrofit
import com.run.config.AppConstants
import com.run.config.modle.BaseModle
import com.run.presenter.LoginHelper
import com.run.presenter.modle.*
import io.reactivex.Observable
import org.json.JSONException
import org.json.JSONObject

object ApiManager {

    private var apiService: ApiService? = null
    private val instance: ApiService
        get() {
            if (apiService == null) {
                synchronized(ApiManager::class.java) {
                    if (apiService == null) {
                        apiService = URetrofit.getInstance(AppConstants.BASE_URL, BaseApplication.context!!)!!.create(ApiService::class.java)
                    }
                }
            }
            return this!!.apiService!!
        }


    /**
     * 获取栏目分类
     */
    fun article(): Observable<ArticleTypeModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return instance.article(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 获取文章列表
     * @param page
     * @return
     */
    fun articlelist(catid: Int, page: Int): Observable<ArticleModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("catid", catid)
            jsonObject.put("page", page)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return instance.articlelist(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 获取排行榜
     *
     * @param page
     * @return
     */
    fun seniority_article(type: String, page: Int): Observable<ArticleModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("type", type)
            jsonObject.put("page", page)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return instance.seniority_article(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 搜索
     *
     * @param word
     * @param page
     * @return
     */
    fun search(word: String?, page: Int): Observable<ArticleModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("word", word)
            jsonObject.put("page", page)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.search(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 获取文章详情页面
     *
     * @param articleid
     * @return
     */
    fun articledetail(articleid: Int): Observable<ArticleDetailModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("articleid", articleid)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.articledetail(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    //========================================用户信息================================================

    /**
     * 请求首页数据
     *
     * @return
     */
    fun index(): Observable<UserJsonModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.index(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 收徒大赛
     */
    fun megagameType(): Observable<MegagameModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.megagameType(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 收徒大赛
     *
     * @return
     */
    fun megagame(): Observable<ContestModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.megagame(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))

    }


    /**
     * 本周活动奖
     */
    fun progress(): Observable<ProgressArtiveModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ApiManager.instance.progress(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))

    }

    /**
     * 转发送现金红包
     */
    fun transmit(): Observable<TransmitModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return ApiManager.instance.transmit(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))

    }


    /**
     * 排行榜
     *
     * @return
     */
    fun seniority(type: String): Observable<SeniorityModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("type", type)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.seniority(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 收益明细
     */
    fun income_record(type: String, page: Int): Observable<IncomeRecordModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("type", type)
            jsonObject.put("page", page)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.income_record(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))

    }

    /**
     * 领取卡券
     *
     * @return
     */
    fun get_voucher(id: Int): Observable<BaseModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("id", id)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.get_voucher(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 我的卡券
     *
     * @return
     */
    fun my_voucher(type: String): Observable<CardModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("type", type)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.my_voucher(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 卡券中心
     *
     * @return
     */
    fun voucher_list(): Observable<CardModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.voucher_list(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))

    }


    /**
     * 体现
     *
     * @return
     */
    fun money(money: Int, type: Int, my_voucherid: Int): Observable<IncomeResultModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("money", money)
            jsonObject.put("type", type)
            jsonObject.put("my_voucherid", my_voucherid)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.money(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))

    }

    /**
     * 获取体现列表
     *
     * @param type 体现类型
     * @param page
     * @return
     */
    fun bill(type: String, page: Int): Observable<WithDrawModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("type", type)
            jsonObject.put("page", page)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.bill(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))

    }


    /**
     * 获取体现方式
     *
     * @return
     */
    fun money_view(): Observable<IncomeModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.money_view(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 收徒信息
     *
     * @return
     */
    fun invite(): Observable<InviteModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.invite(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


    /**
     * 我的收徒列表
     */
    fun invite_list(page: Int): Observable<ApprenticeModle> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("page", page)
            jsonObject.put("channel", AppConstants.CHANNEL_KEY)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ApiManager.instance.invite_list(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }

    /**
     * 记录激活数据
     */
    fun first(imei: String): Observable<BaseModle> {
        val jsonObject = JSONObject()

        jsonObject.put("imei", imei)
        jsonObject.put("channel", AppConstants.CHANNEL_KEY)

        return ApiManager.instance.first(LoginHelper.instance.getmToken(), UEncrypt.encrypt_AES(jsonObject.toString(), AppConstants.DES_KEY))
    }


}
