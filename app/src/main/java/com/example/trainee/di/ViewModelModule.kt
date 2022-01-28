package com.example.trainee.di

import androidx.lifecycle.ViewModel
import com.example.trainee.ui.screens.screen_department_host.DepartmentHostViewModel
import com.example.trainee.ui.screens.screen_error.ErrorViewModel
import com.example.trainee.ui.screens.screen_section_department.DepartmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(DepartmentViewModel::class)]
    fun provideDepartmentViewModel(departmentViewModel: DepartmentViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(DepartmentHostViewModel::class)]
    fun provideDepartmentHostViewModel(departmentHostViewModel: DepartmentHostViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(ErrorViewModel::class)]
    fun provideErrorViewModel(errorFragment: ErrorViewModel): ViewModel

}