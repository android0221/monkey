package com.run.presenter.contract

import android.content.Context
import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.common.utils.ULog
import com.run.common.utils.USystem
import com.run.config.modle.BaseModle
import com.run.presenter.api.ApiManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


interface MainContract {
    interface MainView : BaseMvpView {}
    class MainPresenter(private val v: MainView) : BaseMvpPresenter(v) {
        /**
         * 记录激活页面
         */
        fun statisticsActive(context: Context) {
            addDisposable(ApiManager.first(USystem.getIMEI(context)), object : BaseObserver<BaseModle>() {
                override fun onSuccess(o: BaseModle) {
                    ULog.d("记录激活成功")
                }
                override fun onError(errorType: Int, msg: String?) {
                    ULog.d(msg!!)
                }
            })
        }

    }
}
