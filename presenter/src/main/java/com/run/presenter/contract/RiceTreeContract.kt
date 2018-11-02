package com.run.presenter.contract

import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.PacketModle
import com.run.presenter.modle.PacketResultModle

interface RiceTreeContract {
    interface RiceTreeView : BaseMvpView {
        fun callBackData(modle: PacketModle)
        fun callBackResult(msg: String)
        fun callBackAward(msg: String)
    }


    class RiceTreePresenter(private val v: RiceTreeView) : BaseMvpPresenter(v) {
        fun requestData() {
            addDisposable(ApiManager.my_packet(), object : BaseObserver<PacketModle>() {
                override fun onSuccess(o: PacketModle) {
                    if (isViewAttached()) v.callBackData(o)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })
        }

        fun getPacket(packetId: Int) {
            addDisposable(ApiManager.get_pack(packetId), object : BaseObserver<PacketResultModle>() {
                override fun onSuccess(o: PacketResultModle) {
                    if (isViewAttached()) v.callBackResult(o.money)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })
        }


        fun getAward() {
            addDisposable(ApiManager.receive_award(), object : BaseObserver<BaseModle>() {
                override fun onSuccess(o: BaseModle) {
                    if (isViewAttached()) v.callBackAward(o.msg!!)
                }
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })

        }
    }
}