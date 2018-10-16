package com.run.presenter.api;


import com.run.config.modle.BaseModle;
import com.run.presenter.modle.ApprenticeModle;
import com.run.presenter.modle.ArticleDetailModle;
import com.run.presenter.modle.ArticleModle;
import com.run.presenter.modle.ArticleTypeModle;
import com.run.presenter.modle.CardModle;
import com.run.presenter.modle.ContestModle;
import com.run.presenter.modle.IncomeModle;
import com.run.presenter.modle.IncomeRecordModle;
import com.run.presenter.modle.IncomeResultModle;
import com.run.presenter.modle.InviteModle;
import com.run.presenter.modle.MegagameModle;
import com.run.presenter.modle.ProgressArtiveModle;
import com.run.presenter.modle.SeniorityModle;
import com.run.presenter.modle.UserJsonModle;
import com.run.presenter.modle.WithDrawModle;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {
    /**
     * 获取栏目分类
     *
     * @param content
     * @return
     */
    @GET("web/Article/index")
    Observable<ArticleTypeModle> article(@Header("xytoken") String token, @Query("content") String content);

    /**
     * 获取分类文章列表
     *
     * @param content
     * @return
     */
    @GET("web/Article/lists")
    Observable<ArticleModle> articlelist(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 获取排行榜
     *
     * @param content
     * @return
     */
    @GET("web/Article/seniority_article")
    Observable<ArticleModle> seniority_article(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 获取文章详情
     *
     * @param content
     * @return
     */
    @GET("web/Article/details")
    Observable<ArticleDetailModle> articledetail(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 搜索
     *
     * @param content
     * @return
     */
    @GET("web/Article/search")
    Observable<ArticleModle> search(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 收徒信息
     *
     * @param content
     * @return
     */
    @GET("web/user/invite")
    Observable<InviteModle> invite(@Header("xytoken") String token, @Query("content") String content);

    /**
     * 收徒大赛-是否开启
     */
    @GET("web/user/megagame_type")
    Observable<MegagameModle> megagameType(@Header("xytoken") String token, @Query("content") String content);

    /**
     * 收徒大赛
     */
    @GET("web/user/megagame")
    Observable<ContestModle> megagame(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 收徒信息
     */
    @GET("web/user/invite_list")
    Observable<ApprenticeModle> invite_list(@Header("xytoken") String token, @Query("content") String content);

    /**
     * 个人中心 – 收益明细
     */
    @GET("web/user/income_record?")
    Observable<IncomeRecordModle> income_record(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 排行榜
     *
     * @param content
     * @return
     */
    @GET("web/user/seniority")
    Observable<SeniorityModle> seniority(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 卡券中心
     *
     * @param token
     * @param content
     * @return
     */

    @GET("web/user/voucher_list")
    Observable<CardModle> voucher_list(@Header("xytoken") String token, @Query("content") String content);

    /**
     * 我的卡券
     *
     * @param token
     * @param content
     * @return
     */
    @GET("web/user/my_voucher")
    Observable<CardModle> my_voucher(@Header("xytoken") String token, @Query("content") String content);

    /**
     * 领取卡券
     *
     * @param token
     * @param content
     * @return
     */

    @GET("web/user/get_voucher")
    Observable<BaseModle> get_voucher(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 体现信息
     *
     * @param content
     * @return
     */
    @GET("web/User/money_view")
    Observable<IncomeModle> money_view(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 体现接口
     *
     * @param content
     * @return
     */
    @GET("web/User/money")
    Observable<IncomeResultModle> money(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 体现记录
     *
     * @param content
     * @return
     */
    @GET("web/user/bill")
    Observable<WithDrawModle> bill(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 用户激活记录
     *
     * @param token
     * @param content
     * @return
     */
    @GET("web/config/first")
    Observable<BaseModle> first(@Header("xytoken") String token, @Query("content") String content);


    /**
     * 本周活动奖
     */
    @GET("web/user/progress")
    Observable<ProgressArtiveModle> progress(@Header("xytoken") String token, @Query("content") String content);


    //===============================================用户信息========================================

    /**
     * 用户信息
     *
     * @param content
     * @return
     */
    @GET("web/user/index")
    Observable<UserJsonModle> index(@Header("xytoken") String token, @Query("content") String content);


}
