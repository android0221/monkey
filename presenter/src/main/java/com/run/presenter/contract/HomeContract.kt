package com.run.persioninfomation.presenter


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.ArticleTypeModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 首页操作类
 */
interface HomeContract {
    interface HomeView : BaseMvpView {
        fun showData(lists: List<ArticleTypeModle.DataBean>?)
    }

    class HomePresenter(private val v: HomeView) : BaseMvpPresenter() {
        fun requestData() {
            ApiManager.article()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<ArticleTypeModle>() {
                        override fun onSuccess(o: ArticleTypeModle) {
                            v.showData(o.data)
                        }

                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }
                    })
        }
    }
}
