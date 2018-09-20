package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.ArticleModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 首页操作类
 */
interface ArticleContract {

    interface ArticleView : BaseMvpView {
        fun callBackData(modle: ArticleModle)
    }

    class ArticlePresenter(private val v: ArticleContract.ArticleView) : BaseMvpPresenter() {
        fun requestData(category_id: Int, mpage: Int) {
            ApiManager.articlelist(category_id, mpage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<ArticleModle>() {
                        override fun onSuccess(o: ArticleModle) {
                            v.callBackData(o)
                        }
                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }
                    })
        }
        fun requestData(type: String, mpage: Int) {
            ApiManager.seniority_article(type, mpage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<ArticleModle>() {
                        override fun onSuccess(o: ArticleModle) {
                            v.callBackData(o)
                        }
                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }

                    })
        }

        fun searchData(word: String?, mpage: Int) {
            ApiManager.search(word, mpage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<ArticleModle>() {
                        override fun onSuccess(o: ArticleModle) {
                            v.callBackData(o)
                        }
                        override fun onError(errorType: Int, msg: String?) {
                            v.showErr(errorType, msg!!)
                        }

                    })
        }


    }


}
