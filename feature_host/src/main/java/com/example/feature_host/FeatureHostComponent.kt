package com.example.feature_host

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.api.MultiViewModelFactory
import com.example.core.FeatureScope
import dagger.Component
import kotlin.properties.Delegates

@FeatureScope
@Component(dependencies = [HostDeps::class])
internal interface FeatureHostComponent {

    fun inject(fragment: DepartmentHostFragment)

    @Component.Builder
    interface Builder {

        fun deps(hostDeps: HostDeps): Builder

        fun build(): FeatureHostComponent
    }
}

interface HostDeps {

    val multiViewModelFactory: MultiViewModelFactory
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