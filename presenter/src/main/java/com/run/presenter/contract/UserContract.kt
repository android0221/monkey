package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.login.api.LoginManager
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.login.UserInfoModile
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 首页操作类
 */
interface UserContract {


    interface UserView : BaseMvpView {
        fun showData(bean: UserInfoModile.DataBean)
    }

    class UserPresenter(private val v: UserView) : BaseMvpPresenter(v) {
        fun user_info() {
            if (isViewAttached()) v.showLoading()
            addDisposable(LoginManager.userInfo(), object : BaseObserver<UserInfoModile>() {
                override fun onSuccess(o: UserInfoModile) {
                    if (isViewAttached()) {
                        v.showData(o.data!!)
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


    }
}
