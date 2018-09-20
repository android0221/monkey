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

    class TeacherPresenter(private val v: TeacherView) : BaseMvpPresenter() {
        fun user_info(id: String) {
            LoginManager.boundTeacher(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<BaseModle>() {
                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }

                        override fun onSuccess(o: BaseModle) {
                            v.showSuccess(o.msg!!)
                        }


                    })
        }


    }
}
