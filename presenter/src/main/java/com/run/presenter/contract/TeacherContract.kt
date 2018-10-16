package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.login.api.LoginManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 拜师
 */
interface TeacherContract {


    interface TeacherView : BaseMvpView {
        fun showSuccess(msg: String)
    }

    class TeacherPresenter(private val v: TeacherView) : BaseMvpPresenter(v) {
        fun user_info(id: String) {
            addDisposable(LoginManager.boundTeacher(id), object : BaseObserver<BaseModle>() {
                override fun onSuccess(o: BaseModle) {
                    if (isViewAttached()) v.showSuccess(o.msg!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })

        }


    }
}
