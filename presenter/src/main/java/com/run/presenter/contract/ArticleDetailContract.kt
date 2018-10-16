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

    class ArticlePresenter(private val v: ArticleDetailContract.ArticleView) : BaseMvpPresenter(v) {

        fun requestData(category_id: Int) {
            if (isViewAttached()) v.showLoading()

            addDisposable(ApiManager.articledetail(category_id), object : BaseObserver<ArticleDetailModle>() {
                override fun onSuccess(o: ArticleDetailModle) {
                    if (isViewAttached()) {
                        v.callBackData(o)
                        v.hideLoading()
                    }
                }
                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }

            })


        }


    }


}
