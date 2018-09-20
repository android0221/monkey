package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.login.api.LoginManager
import com.run.presenter.modle.login.DynamicsModle
import com.run.presenter.modle.login.UserItemBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 新手指导操作类
 */
interface ProblemContract {

    interface ProblemView : BaseMvpView {
        fun showData(list: List<UserItemBean>)
    }

    class ProblemPresenter(private val v: ProblemView) : BaseMvpPresenter() {
        fun dynamics(type: Int) {
            LoginManager.dynamics(type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<DynamicsModle>() {
                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }

                        override fun onSuccess(o: DynamicsModle) {
                            v.showData(o.data!!)
                        }


                    })
        }


    }
}
