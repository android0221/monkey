package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.login.api.LoginManager
import com.run.presenter.modle.SignModle

interface SignContract {
    interface SignView : BaseMvpView {
        fun showSignData(modle: SignModle)
        fun callBackSign(key: String)
    }

    class SignPresenter(private val v: SignView) : BaseMvpPresenter(v) {
        fun requestSignInfo() {
            if (isViewAttached()) v.showLoading()
            addDisposable(LoginManager.sign_info(), object : BaseObserver<SignModle>() {
                override fun onSuccess(o: SignModle) {
                    if (isViewAttached()) {
                        v.hideLoading()
                        v.showSignData(o)
                    }
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) {
                        v.hideLoading()
                        v.showErr(errorType, msg!!)
                    }
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

    }
}