package com.example.feature_error.di.module

import com.example.core.di.FeatureScope
import com.example.feature_error.data.ErrorRepositoryImpl
import com.example.feature_error.domain.ErrorRepository
import dagger.Binds
import dagger.Module

@Module
internal interface RepositoryModule {

    @FeatureScope
    @Binds
    fun bindRepository(errorRepositoryImpl: ErrorRepositoryImpl): ErrorRepository
}