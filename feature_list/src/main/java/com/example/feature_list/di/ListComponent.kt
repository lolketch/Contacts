package com.example.feature_list.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.core.ConnectResolver
import com.example.api.RemoteDataSource
import com.example.core.FeatureScope
import com.example.feature_list.di.module.RepositoryModule
import com.example.feature_list.presentation.DepartmentFragment
import dagger.Component
import kotlin.properties.Delegates

@FeatureScope
@Component(
    modules = [RepositoryModule::class],
    dependencies = [DepartmentDeps::class]
)
internal interface ListComponent {

    fun inject(fragment: DepartmentFragment)

    @Component.Builder
    interface Builder {

        fun deps(departmentDeps: DepartmentDeps): Builder

        fun build(): ListComponent
    }
}

interface DepartmentDeps {

    val remoteDataSource: RemoteDataSource

    val connectResolver: ConnectResolver

}

interface DepartmentDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: DepartmentDeps

    companion object : DepartmentDepsProvider by DepartmentDepsStore
}

object DepartmentDepsStore : DepartmentDepsProvider {

    override var deps: DepartmentDeps by Delegates.notNull()
}

internal class FeatureDepartmentComponentViewModel : ViewModel() {

    val newComponent =
        DaggerListComponent.builder().deps(DepartmentDepsProvider.deps).build()
}