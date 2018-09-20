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

    class WithDrawBillPresenter(private val v: WithDrawBillView) : BaseMvpPresenter() {
        fun bill(type: String, mPage: Int) {
            ApiManager.bill(type, mPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<WithDrawModle>() {
                        override fun onSuccess(o: WithDrawModle) {
                            v.showData(o.data)
                        }

                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }
                    })
        }


    }
}
