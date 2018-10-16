package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.ContestModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 收徒大赛
 */
interface ContestContract {

    interface ContestView : BaseMvpView {
        fun showData(modle: ContestModle)
    }

    class ContestPresenter(private val v: ContestView) : BaseMvpPresenter(v) {
        fun megagame() {
            addDisposable(ApiManager.megagame(), object : BaseObserver<ContestModle>() {
                override fun onSuccess(o: ContestModle) {
                    if (isViewAttached()) v.showData(o)
                }
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })

        }


    }
}
