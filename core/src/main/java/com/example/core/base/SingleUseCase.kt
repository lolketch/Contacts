package com.example.core.base

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T> {

    protected abstract fun buildUseCaseSingle(): Single<T>
    private val compositeDisposable = CompositeDisposable()

    fun execute(
        onLoading: () -> Unit = {},
        onSuccess: ((t: T) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ) {
        onLoading()
        compositeDisposable.add(
            buildUseCaseSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun dispose() {
        compositeDisposable.clear()
    }
}
