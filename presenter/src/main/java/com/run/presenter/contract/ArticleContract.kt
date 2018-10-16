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

    class ArticlePresenter(private val v: ArticleContract.ArticleView) : BaseMvpPresenter(v) {
        fun requestData(category_id: Int, mpage: Int) {
            addDisposable(ApiManager.articlelist(category_id,mpage) ,object: BaseObserver<ArticleModle>(){
                override fun onSuccess(o: ArticleModle) {
                    if(isViewAttached())  v.callBackData(o)
                }
                override fun onError(errorType: Int, msg: String?) {
                    if(isViewAttached())v.showErr(errorType,msg!!)
                }
            })

        }
        fun requestData(type: String, mpage: Int) {
            addDisposable(ApiManager.seniority_article(type,mpage),object:BaseObserver<ArticleModle>(){
                override fun onSuccess(o: ArticleModle) {
                    if(isViewAttached()) v.callBackData(o)
                }
                override fun onError(errorType: Int, msg: String?) {
                    if(isViewAttached())v.showErr(errorType,msg!!)
                }
            })


        }

        fun searchData(word: String?, mpage: Int) {
            addDisposable(ApiManager.search(word,mpage),object:BaseObserver<ArticleModle>(){
                override fun onSuccess(o: ArticleModle) {
                    if(isViewAttached())v.callBackData(o)
                }

                override fun onError(errorType: Int, msg: String?) {
                    if(isViewAttached())v.showErr(errorType,msg!!)
                }
            })


        }


    }


}
