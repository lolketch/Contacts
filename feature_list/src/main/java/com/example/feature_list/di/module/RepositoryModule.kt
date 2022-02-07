package com.example.feature_list.di.module

import com.example.core.FeatureScope
import com.example.feature_list.data.DepartmentRepositoryImpl
import com.example.feature_list.domain.DepartmentRepository
import dagger.Binds
import dagger.Module

@Module
internal interface RepositoryModule {

    @FeatureScope
    @Binds
    fun bindRepository(departmentRepositoryImpl: DepartmentRepositoryImpl): DepartmentRepository
}