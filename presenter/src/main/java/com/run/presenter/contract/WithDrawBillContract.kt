package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.IncomeBean
import com.run.presenter.modle.WithDrawModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 提现记录
 */
interface WithDrawBillContract {

    interface WithDrawBillView : BaseMvpView {
        fun showData(modle: List<IncomeBean>)
    }

    class WithDrawBillPresenter(private val v: WithDrawBillView) : BaseMvpPresenter(v) {
        fun bill(type: String, mPage: Int) {
            addDisposable(ApiManager.bill(type, mPage), object : BaseObserver<WithDrawModle>() {
                override fun onSuccess(o: WithDrawModle) {
                    if (isViewAttached()) v.showData(o.data)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })


        }


    }
}
