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

    class CardPresenter(private val v: CardView) : BaseMvpPresenter(v) {
        fun requestData(type: String) {
            addDisposable(ApiManager.my_voucher(type), object : BaseObserver<CardModle>() {
                override fun onSuccess(o: CardModle) {
                    if (isViewAttached()) v.showData(o.data)
                }
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }
            })


        }

        /**
         * 卡券中心
         */
        fun voucher_list() {
            addDisposable(ApiManager.voucher_list(), object : BaseObserver<CardModle>() {
                override fun onSuccess(o: CardModle) {
                    if (isViewAttached()) v.showData(o.data)
                }
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }
            })


        }

        /**
         * 领取卡券
         * @param cardId
         */
        fun get_voucher(cardId: Int) {
            addDisposable(ApiManager.get_voucher(cardId), object : BaseObserver<BaseModle>() {
                override fun onSuccess(o: BaseModle) {

                }
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })

        }
    }
}
