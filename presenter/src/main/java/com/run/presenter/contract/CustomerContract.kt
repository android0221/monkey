package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.login.api.LoginManager
import com.run.presenter.modle.login.QQModle

interface CustomerContract {
    interface CustomerView : BaseMvpView {
        fun callBackQQKey(key: String,wechat:String)
    }

    class CustomerPresenter(private val v: CustomerView) : BaseMvpPresenter(v) {
        /*
                 *获取到QQ群的key
                 */
        fun getQQKey() {
            addDisposable(LoginManager.getQQKey(), object : BaseObserver<QQModle>() {
                override fun onSuccess(o: QQModle) {
                    if (isViewAttached()) v.callBackQQKey(o.key!!,o.wechat!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })


        }
    }
}