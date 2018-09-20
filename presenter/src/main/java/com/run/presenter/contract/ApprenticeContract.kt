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

    class ApprenticePresenter(private val v: ApprenticeView) : BaseMvpPresenter() {
        fun invite_list(mPage: Int) {
            ApiManager.invite_list(mPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<ApprenticeModle>() {
                        override fun onSuccess(o: ApprenticeModle) {
                            v.showData(o.data!!)

                        }

                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)

                        }
                    })
        }


    }
}
