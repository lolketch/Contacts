package com.example.trainee.di

import android.app.Application
import com.example.trainee.ui.screens.screen_department_host.DepartmentHostFragment
import com.example.trainee.ui.screens.screen_section_department.DepartmentFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        ViewModelModule::class
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
}