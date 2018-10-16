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

    class ProblemPresenter(private val v: ProblemView) : BaseMvpPresenter(v) {
        fun dynamics(type: Int) {
            addDisposable(LoginManager.dynamics(type),object:BaseObserver<DynamicsModle>(){
                override fun onSuccess(o: DynamicsModle) {
                    if(isViewAttached()) v.showData(o.data!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if(isViewAttached())v.showErr(errorType,msg!!)
                }

            })



        }


    }
}
