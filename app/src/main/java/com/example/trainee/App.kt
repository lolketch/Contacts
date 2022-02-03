package com.example.trainee

import android.app.Application
import com.example.feature_error.ErrorDepsStore
import com.example.feature_host.di.HostDepsStore
import com.example.feature_list.di.DepartmentDepsStore

class App : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        HostDepsStore.deps = appComponent
        DepartmentDepsStore.deps = appComponent
        ErrorDepsStore.deps = appComponent

    }
}