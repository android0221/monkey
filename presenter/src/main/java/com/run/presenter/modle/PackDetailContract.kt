package com.run.presenter.modle

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager

interface PackDetailContract {
    interface PackDetailView : BaseMvpView {
        fun callBackData(list: List<PackageDetalModle.PackBean>)
    }

    class PackDetailPresenter(private val v: PackDetailView) : BaseMvpPresenter(v) {
        fun requestData(page: Int) {
            addDisposable(ApiManager.pack_list(page), object : BaseObserver<PackageDetalModle>() {
                override fun onSuccess(o: PackageDetalModle) {
                    if (isViewAttached()) v.callBackData(o.list)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })
        }
    }
}