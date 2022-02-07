package com.example.feature_host.data

import com.example.core.ConnectResolver
import com.example.feature_host.domain.HostRepository
import javax.inject.Inject

internal class HostRepositoryImpl @Inject constructor(private val connectResolver: ConnectResolver): HostRepository {
    override fun checkConnection(): Boolean {
        return connectResolver.isOnline()
    }
}