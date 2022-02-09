package com.example.core

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface SchedulerProvider {
    fun computation(): Scheduler
    fun trampoline(): Scheduler
    fun mainThread(): Scheduler
    fun io(): Scheduler
}

class AppSchedulerProvider @Inject constructor() : SchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun trampoline(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun mainThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }
}