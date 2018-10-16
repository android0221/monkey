package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.TransmitModle

interface TransmitContract {
    interface TransmitView : BaseMvpView {

        fun showData(modle:TransmitModle)
    }

    class TransmitPresenter(private val v: TransmitView) : BaseMvpPresenter(v) {

        fun requestData() {
            if(isViewAttached())v.showLoading()
            addDisposable(ApiManager.transmit(), object : BaseObserver<TransmitModle>() {
                override fun onSuccess(o: TransmitModle) {
                    if(isViewAttached()){
                        v.showData(o)
                        v.hideLoading()
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
    }
}