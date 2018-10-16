package com.run.common.base

import com.run.common.utils.UCompositeDisposable
import io.reactivex.Observable


abstract class BaseMvpPresenter(v: BaseMvpView) {

    var view: BaseMvpView? = null

    init {
        this.view = v
    }

    /**
     * 是否存在
     */
    fun isViewAttached(): Boolean {
        return view != null
    }

    /**
     * 添加网络请求
     */
    protected fun addDisposable(observable: Observable<*>, observer: BaseObserver<*>) {
        UCompositeDisposable.getInstance().addDisposable(observable, observer)
    }
}
