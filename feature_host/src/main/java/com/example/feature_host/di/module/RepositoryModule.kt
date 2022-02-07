package com.example.feature_host.di.module

import com.example.core.FeatureScope
import com.example.feature_host.data.HostRepositoryImpl
import com.example.feature_host.domain.HostRepository
import dagger.Binds
import dagger.Module

@Module
internal interface RepositoryModule {

    @FeatureScope
    @Binds
    fun bindRepository(hostRepositoryImpl: HostRepositoryImpl): HostRepository

}