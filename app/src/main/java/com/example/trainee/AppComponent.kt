package com.example.trainee

import android.app.Application
import com.example.api.ConnectResolver
import com.example.api.MultiViewModelFactory
import com.example.api.RemoteDataSource
import com.example.feature_error.di.ErrorDeps
import com.example.feature_host.di.HostDeps
import com.example.feature_list.di.DepartmentDeps
import com.example.trainee.di.NetworkModule
import com.example.trainee.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent: HostDeps, DepartmentDeps, ErrorDeps {

    override val remoteDataSource: RemoteDataSource

    override val connectResolver: ConnectResolver

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}

@Module(
    includes = [
        NetworkModule::class,
        ViewModelModule::class
    ]
)
class AppModule

@Scope
annotation class AppScope