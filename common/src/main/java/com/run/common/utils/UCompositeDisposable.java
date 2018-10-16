package com.run.common.utils;

import com.run.common.base.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UCompositeDisposable {

    //单例模式
    private UCompositeDisposable() {
        compositeDisposable = new CompositeDisposable();
    }

    private static UCompositeDisposable uCompositeDisposable;

    public static UCompositeDisposable getInstance() {
        if (uCompositeDisposable == null) {
            synchronized (UCompositeDisposable.class) {
                if (uCompositeDisposable == null) {
                    uCompositeDisposable = new UCompositeDisposable();
                }
            }
        }
        return uCompositeDisposable;
    }


    private CompositeDisposable compositeDisposable;

    public void addDisposable(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }
}
