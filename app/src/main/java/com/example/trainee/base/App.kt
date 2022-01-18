package com.example.trainee.base

import android.app.Application
import com.example.trainee.di.AppComponent
import com.example.trainee.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}