package com.example.trainee.di

import androidx.lifecycle.ViewModel
import com.example.feature_error.ErrorViewModel
import com.example.feature_host.DepartmentHostViewModel
import com.example.feature_list.DepartmentViewModel
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