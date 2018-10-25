package com.run.login.api


import com.run.config.modle.BaseModle
import com.run.login.modle.VersionModle
import com.run.presenter.modle.login.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * 登录接口
 */
interface LoginService {

    //=========================================登录=======================================

    /**
     * 登录
     *
     * @param content
     * @return
     */
    @GET("web/login/verification_login?")
    fun verificationLogin(@Query("content") content: String): Observable<LoginModle>


    //获取验证码接口
    @GET("web/register/code?")
    fun getCode(@Query("content") content: String): Observable<BaseModle>

    /**
     * 用户注册
     * @param content
     * @return
     */
    @GET("web/register/verifyRegister?")
    fun verifyRegister(@Query("content") content: String): Observable<BaseModle>

    /**
     * 重置密码
     *
     * @param content
     * @return
     */
    @GET("web/Reset/retrieve_password")
    fun retrievePassword(@Query("content") content: String): Observable<BaseModle>


    /**
     * 个人中心 – 退出登录
     *
     * @param content
     * @return
     */
    @GET("web/login/logout")
    fun logout(@Header("xytoken") token: String, @Query("content") content: String): Observable<BaseModle>
    //========================================版本更新=====================================

    /**
     * 获取版本跟新信息
     */
    @GET("web/Config/versions")
    fun versions(@Header("xytoken") token: String, @Query("content") content: String): Observable<VersionModle>


    /**
     * 获取QQEKY值
     *
     * @param token
     * @param content
     * @return
     */
    @GET(" web/config/getqq")
    fun getQQKey(@Header("xytoken") token: String, @Query("content") content: String): Observable<QQModle>


    /**
     * 意见反馈
     *
     * @param content
     * @return
     */
    @GET("/web/User/feedback")
    fun feedback(@Header("xytoken") token: String, @Query("content") content: String): Observable<BaseModle>


    /**
     * 常见问题列表
     */
    @GET("web/user/dynamics")
    fun dynamics(@Header("xytoken") token: String, @Query("content") content: String): Observable<DynamicsModle>

    /**
     * 常见问题内容
     */
    @GET("web/user/d_content")
    fun problemContent(@Header("xytoken") token: String, @Query("content") content: String): Observable<DyContentModle>

    /**
     * 个人中心 – 签到
     *
     * @param content
     * @return
     */
    @GET("web/user/sign")
    fun sign(@Header("xytoken") token: String, @Query("content") content: String): Observable<BaseModle>


    /**
     * 首页弹窗
     *
     * @param token
     * @param content
     * @return
     */
    @GET("web/config/activity")
    fun activity(@Header("xytoken") token: String, @Query("content") content: String): Observable<TitleModle>


    /**
     * 个人中心 – 用户设置
     */
    @GET("web/user/user_info")
    fun userInfo(@Header("xytoken") token: String, @Query("content") content: String): Observable<UserInfoModile>


    /**
     * 拜师操作
     *
     * @param content
     * @return
     */
    @GET("/web/User/bound_teacher")
    fun boundTeacher(@Header("xytoken") token: String, @Query("content") content: String): Observable<BaseModle>


    //==========================================分享======================================
    /**
     * 获取分享内容
     * @param content
     * @return
     */
    @GET("web/Article/share_record")
    fun shareRecord(@Header("xytoken") token: String, @Query("content") content: String): Observable<ShareModle>

}
