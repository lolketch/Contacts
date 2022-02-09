package com.example.feature_error.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.core.ConnectResolver
import com.example.core.di.FeatureScope
import com.example.feature_error.di.module.RepositoryModule
import com.example.feature_error.presentation.ErrorFragment
import dagger.Component
import kotlin.properties.Delegates

@FeatureScope
@Component(dependencies = [ErrorDeps::class], modules = [RepositoryModule::class])
internal interface ErrorComponent {

    fun inject(fragment: ErrorFragment)

    @Component.Builder
    interface Builder {

        fun deps(charactersDeps: ErrorDeps): Builder

        fun build(): ErrorComponent
    }
}

interface ErrorDeps {

    val connectResolver: ConnectResolver
}

interface ErrorDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: ErrorDeps

    companion object : ErrorDepsProvider by ErrorDepsStore
}

object ErrorDepsStore : ErrorDepsProvider {

    override var deps: ErrorDeps by Delegates.notNull()
}

internal class FeatureErrorComponentViewModel : ViewModel() {

    val newComponent = DaggerErrorComponent.builder().deps(ErrorDepsProvider.deps).build()
}