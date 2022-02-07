package com.example.feature_error.data

import com.example.core.ConnectResolver
import com.example.feature_error.domain.ErrorRepository
import javax.inject.Inject

internal class ErrorRepositoryImpl @Inject constructor(private val connectResolver: ConnectResolver): ErrorRepository {
    override fun checkConnection(): Boolean {
        return connectResolver.isOnline()
    }
}