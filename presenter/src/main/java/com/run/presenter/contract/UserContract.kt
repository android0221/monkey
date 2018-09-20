package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.login.api.LoginManager
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

    class UserPresenter(private val v: UserView) : BaseMvpPresenter() {
        fun user_info() {
            v.showLoading()
            LoginManager.userInfo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<UserInfoModile>() {
                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                            v.hideLoading()
                        }

                        override fun onSuccess(o: UserInfoModile) {
                            v.showData(o.data!!)
                            v.hideLoading()
                        }


                    })
        }


    }
}
