package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.login.api.LoginManager
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.InformModle
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
        fun callBacInform(key: String)
        fun callBackMegagame(invite_top_img: String, type: String)
    }

    class PersionPresenter(private val v: PersionView) : BaseMvpPresenter(v) {
        /**
         * 获取用户信息
         */
        fun getUserInfo() {
            addDisposable(ApiManager.index(), object : BaseObserver<UserJsonModle>() {
                override fun onSuccess(o: UserJsonModle) {
                    if (isViewAttached()) v.callBackUserData(o)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }
            })


        }


        /*
         *获取到QQ群的key
         */
        fun getQQKey() {
            addDisposable(LoginManager.getQQKey(), object : BaseObserver<QQModle>() {
                override fun onSuccess(o: QQModle) {
                    if (isViewAttached()) v.callBackQQKey(o.key!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })


        }

        /**
         * 签到
         */
        fun sign() {
            addDisposable(LoginManager.sign(), object : BaseObserver<BaseModle>() {
                override fun onSuccess(o: BaseModle) {
                    if (isViewAttached()) v.callBackSign(o.msg!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })

        }

        fun inform() {
            addDisposable(LoginManager.inform(), object : BaseObserver<InformModle>() {
                override fun onSuccess(o: InformModle) {
                    if (isViewAttached()) v.callBacInform(o.content!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })

        }

        /**
         * 是否显示收徒大赛信息
         */
        fun megagameType() {
            addDisposable(ApiManager.megagameType(), object : BaseObserver<MegagameModle>() {
                override fun onSuccess(o: MegagameModle) {
                    if (isViewAttached()) v.callBackMegagame(o.invite_top_img!!, o.activity_type!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }
            })

        }

    }
}