package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.IncomeRecordModle

/**
 * 收益明细
 */
interface WalletContract {


    interface WalletView : BaseMvpView {
        fun showData(bean: IncomeRecordModle.MoneyBean)
    }

    class WalletPresenter(private val v: WalletContract.WalletView) : BaseMvpPresenter(v) {
        fun requestData(type: String, mpage: Int) {
            addDisposable(ApiManager.income_record(type, mpage), object : BaseObserver<IncomeRecordModle>() {
                override fun onSuccess(o: IncomeRecordModle) {
                    if (isViewAttached()) v.showData(o.userinfo!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }
            })


        }


    }
}
