package com.example.feature_host.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.api.ConnectResolver
import com.example.api.MultiViewModelFactory
import com.example.core.FeatureScope
import com.example.feature_host.data.HostRepositoryImpl
import com.example.feature_host.domain.HostRepository
import com.example.feature_host.presentation.DepartmentHostFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import kotlin.properties.Delegates

@FeatureScope
@Component(dependencies = [HostDeps::class], modules = [RepositoryModule::class])
internal interface FeatureHostComponent {

    fun inject(fragment: DepartmentHostFragment)

    @Component.Builder
    interface Builder {

        fun deps(hostDeps: HostDeps): Builder

        fun build(): FeatureHostComponent
    }
}

interface HostDeps {

    val connectResolver: ConnectResolver
}

interface HostDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: HostDeps

    companion object : HostDepsProvider by HostDepsStore
}

object HostDepsStore : HostDepsProvider {

    override var deps: HostDeps by Delegates.notNull()
}

internal class FeatureHostComponentViewModel : ViewModel() {
    val newComponent = DaggerFeatureHostComponent.builder().deps(HostDepsProvider.deps).build()
}

@Module
internal interface RepositoryModule {

    @FeatureScope
    @Binds
    fun bindRepository(hostRepositoryImpl: HostRepositoryImpl): HostRepository

}