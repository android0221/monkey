package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.SeniorityModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 收益排行榜
 */
interface SeniorityContract {


    interface SeniorityView : BaseMvpView {
        fun showData(modle: SeniorityModle)
    }

    class SeniorityPresenter(private val v: SeniorityView) : BaseMvpPresenter(v) {
        fun requestData(type: String) {
            addDisposable(ApiManager.seniority(type), object : BaseObserver<SeniorityModle>() {
                override fun onSuccess(o: SeniorityModle) {
                    if (isViewAttached()) v.showData(o)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })

        }


    }
}
