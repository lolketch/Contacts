package com.example.trainee.di

import android.app.Application
import com.example.trainee.ui.screens.screen_department_host.DepartmentHostFragment
import com.example.trainee.ui.screens.screen_error.ErrorFragment
import com.example.trainee.ui.screens.screen_section_department.DepartmentFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class
    ]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(departmentHostFragment: DepartmentHostFragment)
    fun inject(departmentFragment: DepartmentFragment)
    fun inject(errorFragment: ErrorFragment)
}

@Module(
    includes = [
        NetworkModule::class,
        ViewModelModule::class
    ]
)
class AppModule