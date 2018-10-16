package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.login.api.LoginManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 首页操作类
 */
interface FeedBackContract {


    interface FeedBackView : BaseMvpView {
        fun submitSucdess(msg: String)
    }

    class FeedBackPresenter(private val v: FeedBackView) : BaseMvpPresenter(v) {


        fun feedBack(title: String, content: String, phone: String) {

            addDisposable(LoginManager.feedback(title, content, phone), object : BaseObserver<BaseModle>() {
                override fun onSuccess(o: BaseModle) {
                    if (isViewAttached()) v.submitSucdess(o.msg!!)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }
            })


        }


    }
}
