package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.ApprenticeBean
import com.run.presenter.modle.ApprenticeModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 徒弟列表
 */
interface ApprenticeContract {

    interface ApprenticeView : BaseMvpView {
        fun showData(list: List<ApprenticeBean>)
    }

    class ApprenticePresenter(private val v: ApprenticeView) : BaseMvpPresenter(v) {
        fun invite_list(mPage: Int) {

            addDisposable(ApiManager.invite_list(mPage), object : BaseObserver<ApprenticeModle>() {
                override fun onSuccess(o: ApprenticeModle) {
                    if (isViewAttached()) v.showData(o.data!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }
            })

        }


    }
}
