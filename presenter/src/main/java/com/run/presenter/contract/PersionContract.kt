package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.login.api.LoginManager
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.MegagameModle
import com.run.presenter.modle.UserJsonModle
import com.run.presenter.modle.login.QQModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 用户信息
 */
interface PersionContract {
    interface PersionView : BaseMvpView {
        fun callBackUserData(modle: UserJsonModle)
        fun callBackQQKey(key: String)
        fun callBackSign(key: String)
        fun callBackMegagame(invite_top_img: String, type: String)
    }

    class PersionPresenter(private val v: PersionView) : BaseMvpPresenter() {
        /**
         * 获取用户信息
         */
        fun getUserInfo() {
            ApiManager.index().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<UserJsonModle>() {
                        override fun onSuccess(o: UserJsonModle) {
                            v.callBackUserData(o)
                        }

                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }

                    })
        }


        /*
         *获取到QQ群的key
         */
        fun getQQKey() {
            LoginManager.getQQKey().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<QQModle>() {
                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }

                        override fun onSuccess(o: QQModle) {
                            v.callBackQQKey(o.key!!)
                        }

                    })
        }

        /**
         * 签到
         */
        fun sign() {
            LoginManager.sign().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<BaseModle>() {
                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }

                        override fun onSuccess(o: BaseModle) {
                            v.callBackSign(o.msg!!)
                        }

                    })
        }

        /**
         * 是否显示收徒大赛信息
         */
        fun megagameType() {
            ApiManager.megagameType().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<MegagameModle>() {
                        override fun onSuccess(o: MegagameModle) {
                            v.callBackMegagame(o.invite_top_img!!, o.activity_type!!)
                        }

                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }
                    })
        }

    }
}