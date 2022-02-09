package com.example.core.di

import com.example.core.AppSchedulerProvider
import com.example.core.SchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface SchedulerModule {

    @FeatureScope
    @Binds
    fun bindScheduler(appSchedulerProvider: AppSchedulerProvider): SchedulerProvider
}