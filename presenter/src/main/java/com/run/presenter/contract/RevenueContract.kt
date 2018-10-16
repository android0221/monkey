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

    class RevenuePresenter(private val v: RevenueView) : BaseMvpPresenter(v) {
        fun requestData(type: String, mpage: Int) {
            addDisposable(ApiManager.income_record(type,mpage),object:BaseObserver<IncomeRecordModle>(){
                override fun onSuccess(o: IncomeRecordModle) {
                    if(isViewAttached()) v.showData(o.data)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if(isViewAttached())v.showErr(errorType,msg!!)
                }
            })


        }


    }
}
