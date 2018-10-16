package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.login.api.LoginManager
import com.run.presenter.LoginHelper

interface SettingContract {

    interface SettingView : BaseMvpView {

        fun callBackLogout(msg: String)
    }


    class SettingPresenter(private val v: SettingView) : BaseMvpPresenter(v) {

        fun logout() {
            if (isViewAttached()) v.showLoading()
            addDisposable(LoginManager.logout(), object : BaseObserver<BaseModle>() {
                override fun onSuccess(o: BaseModle) {
                    LoginHelper.instance.setmToken("")
                    if (isViewAttached()) v.callBackLogout(o.msg!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) {
                        v.hideLoading()
                        v.showErr(errorType, msg!!)
                    }
                }
            })
        }


    }

}