package com.run.presenter.contract.login


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.login.api.LoginManager
import com.run.presenter.LoginHelper
import com.run.presenter.modle.login.LoginModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface LoginContract {
    interface LoginView : BaseMvpView {
        fun callBackLogin()
    }

    class LoginPresenter(private val v: LoginContract.LoginView) : BaseMvpPresenter(v) {
        fun login(mobile: String, password: String) {
            addDisposable(LoginManager.verificationLogin(mobile, password), object : BaseObserver<LoginModle>() {
                override fun onSuccess(o: LoginModle) {
                    LoginHelper.instance.setmToken(o.token!!)
                    if (isViewAttached()) {
                        v.showMsg("登录成功")
                        v.callBackLogin()
                    }
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })


        }
    }
}
