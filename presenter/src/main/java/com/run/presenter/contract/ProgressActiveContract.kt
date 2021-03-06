package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.ProgressArtiveModle

interface ProgressActiveContract {
    interface ProgressView : BaseMvpView {
        fun showData(modle: ProgressArtiveModle)
    }

    class ProgeressActivityPresenter(private val v: ProgressView) : BaseMvpPresenter(v) {

        fun requestData() {
            if (isViewAttached()) v.showLoading()
            addDisposable(ApiManager.progress(), object : BaseObserver<ProgressArtiveModle>() {
                override fun onSuccess(o: ProgressArtiveModle) {
                    if (isViewAttached()) {
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