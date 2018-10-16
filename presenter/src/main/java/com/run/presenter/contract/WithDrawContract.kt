package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.IncomeModle
import com.run.presenter.modle.IncomeResultModle
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 提现
 */
interface WithDrawContract {

    interface WithDrawView : BaseMvpView {
        fun showData(modle: IncomeModle)

        fun gotoBindWC(msg: String, url: String,content:String)

        fun moneyFinish(msg: String)

        fun showMoneyError(msg: String)
    }

    class WithDrawPresenter(private val v: WithDrawView) : BaseMvpPresenter(v) {
        fun money_view() {
            if (isViewAttached()) v.showLoading()
            addDisposable(ApiManager.money_view(), object : BaseObserver<IncomeModle>() {
                override fun onSuccess(o: IncomeModle) {
                    if (isViewAttached()) {
                        v.showData(o)
                        v.hideLoading()
                    }
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) {
                        v.showErr(errorType, msg!!)
                        v.hideLoading()
                    }
                }

            })

        }

        fun money(money: Int, type: Int, my_voucherid: Int) {
            if (isViewAttached()) v.showLoading()

            ApiManager.money(money, type, my_voucherid).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<IncomeResultModle> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(bean: IncomeResultModle) {
                            v.hideLoading()
                            if (bean == null) return
                            if (bean.status == -100) {
                                when (bean.code) {
                                    10045 -> if (isViewAttached()) v.gotoBindWC(bean!!.msg!!, bean.url!!,bean.content!!)
                                    else -> if (isViewAttached()) v.showMoneyError(bean!!.msg!!)
                                }
                                return
                            }
                            if (bean.status == 200) {
                                if (isViewAttached()) v.moneyFinish(bean!!.msg!!)
                            }
                        }
                        override fun onError(e: Throwable) {
                            if (isViewAttached()) v.showMsg("提现失败")
                        }
                        override fun onComplete() {
                        }
                    })
        }

    }
}
