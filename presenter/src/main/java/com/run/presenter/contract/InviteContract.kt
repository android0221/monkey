package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.InviteModle
import com.run.presenter.modle.SeniorityModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 邀请收徒
 */
interface InviteContract {
    interface InviteView : BaseMvpView {
        fun showInvite(modle: InviteModle)
        fun showData(modle: SeniorityModle)
    }

    class InvitePresenter(private val v: InviteView) : BaseMvpPresenter() {
        /**
         * 收徒信息
         */
        fun invite() {
            ApiManager.invite().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<InviteModle>() {
                        override fun onSuccess(o: InviteModle) {
                            v.showInvite(o)
                        }

                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }
                    })
        }

        fun seniority(type: String) {
            ApiManager.seniority(type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<SeniorityModle>() {
                        override fun onSuccess(o: SeniorityModle) {
                            v.showData(o)
                        }

                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }
                    })
        }


    }
}
