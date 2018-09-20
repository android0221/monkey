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

    class SeniorityPresenter(private val v: SeniorityView) : BaseMvpPresenter() {
        fun requestData(type: String) {
            ApiManager.seniority(type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<SeniorityModle>() {
                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }

                        override fun onSuccess(o: SeniorityModle) {
                            v.showData(o)
                        }
                    })
        }


    }
}
