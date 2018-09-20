package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.ArticleDetailModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 文章内容详情页面
 */
interface ArticleDetailContract {

    interface ArticleView : BaseMvpView {
        fun callBackData(modle: ArticleDetailModle)
    }

    class ArticlePresenter(private val v: ArticleDetailContract.ArticleView) : BaseMvpPresenter() {

        fun requestData(category_id: Int) {
            v.showLoading()
            ApiManager.articledetail(category_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<ArticleDetailModle>() {
                        override fun onSuccess(o: ArticleDetailModle) {
                            v.callBackData(o)
                            v.hideLoading()
                        }

                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }
                    })
        }


    }


}
