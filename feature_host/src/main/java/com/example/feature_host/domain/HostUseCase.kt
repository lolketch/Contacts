package com.example.feature_host.domain

import javax.inject.Inject

internal class HostUseCase @Inject constructor(private val repository: HostRepository) {
    fun checkConnection(): Boolean {
        return repository.checkConnection()
    }
}