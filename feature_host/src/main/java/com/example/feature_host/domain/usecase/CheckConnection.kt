package com.example.feature_host.domain.usecase

import com.example.feature_host.domain.HostRepository
import javax.inject.Inject

internal class CheckConnection @Inject constructor(private val repository: HostRepository) {
    fun checkConnection(): Boolean {
        return repository.checkConnection()
    }
}