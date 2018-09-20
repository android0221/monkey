package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.CardBean
import com.run.presenter.modle.CardModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 卡券
 */
interface CardContract {


    interface CardView : BaseMvpView {
        fun showData(modle: List<CardBean>?)
    }

    class CardPresenter(private val v: CardView) : BaseMvpPresenter() {
        fun requestData(type: String) {
            ApiManager.my_voucher(type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<CardModle>() {
                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }

                        override fun onSuccess(o: CardModle) {
                            v.showData(o.data)
                        }
                    })
        }

        /**
         * 卡券中心
         */
        fun voucher_list() {
            ApiManager.voucher_list()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<CardModle>() {
                        override fun onSuccess(o: CardModle) {
                            v.showData(o.data)
                        }

                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }

                    })

        }

        /**
         * 领取卡券
         * @param cardId
         */
        fun get_voucher(cardId: Int) {
            ApiManager.get_voucher(cardId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<BaseModle>() {
                        override fun onSuccess(o: BaseModle) {}
                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)

                        }
                    })
        }
    }
}
