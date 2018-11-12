package com.run.presenter.contract


import com.run.common.base.BaseMvpPresenter
import com.run.common.base.BaseMvpView
import com.run.common.base.BaseObserver
import com.run.config.modle.BaseModle
import com.run.presenter.api.ApiManager
import com.run.presenter.modle.ArticleModle
import com.run.presenter.modle.ShareListModle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 首页操作类
 */
interface ArticleContract {

    interface ArticleView : BaseMvpView {
        fun callBackData(modle: ArticleModle)

        fun callBackShareData(list: List<ShareListModle.ShareDataBean>)
    }

    class ArticlePresenter(private val v: ArticleContract.ArticleView) : BaseMvpPresenter(v) {
        fun requestData(category_id: Int, mpage: Int) {
            addDisposable(ApiManager.articlelist(category_id, mpage), object : BaseObserver<ArticleModle>() {
                override fun onSuccess(o: ArticleModle) {
                    if (isViewAttached()) v.callBackData(o)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }
            })

        }

        fun requestData(type: String, mpage: Int) {
            addDisposable(ApiManager.seniority_article(type, mpage), object : BaseObserver<ArticleModle>() {
                override fun onSuccess(o: ArticleModle) {
                    if (isViewAttached()) v.callBackData(o)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }
            })


        }


        /**
         * 获取分享列表
         */
        fun doShareList(shareidList: String) {
            if (isViewAttached()) {
                v.showLoading()
            }
            addDisposable(ApiManager.share_list(shareidList), object : BaseObserver<ShareListModle>() {
                override fun onSuccess(o: ShareListModle) {
                    if (isViewAttached()) {
                        v.hideLoading()
                        v.callBackShareData(o.share_data!!)
                    }
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) {
                        v.hideLoading()
                        v.showMsg(msg)
                    }
                }

            })
        }


        fun searchData(word: String?, mpage: Int) {
            addDisposable(ApiManager.search(word, mpage), object : BaseObserver<ArticleModle>() {
                override fun onSuccess(o: ArticleModle) {
                    if (isViewAttached()) v.callBackData(o)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if (isViewAttached()) v.showErr(errorType, msg!!)
                }
            })


        }


    }


}
