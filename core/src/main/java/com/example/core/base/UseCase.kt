package com.example.core.base

import com.example.core.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class UseCase<T> (
    private val schedulerProvider: SchedulerProvider
) {

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
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun dispose() {
        compositeDisposable.clear()
    }
}