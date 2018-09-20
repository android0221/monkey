package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.IncomeRecordModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 收益明细
 */
interface RevenueContract {


    interface RevenueView : BaseMvpView {
        fun showData(modle: List<IncomeRecordModle.DataBean>?)
    }

    class RevenuePresenter(private val v: RevenueView) : BaseMvpPresenter() {
        fun requestData(type: String, mpage: Int) {
            ApiManager.income_record(type, mpage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<IncomeRecordModle>() {
                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }
                        override fun onSuccess(o: IncomeRecordModle) {
                            v.showData(o.data)
                        }


                    })
        }


    }
}
