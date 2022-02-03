package com.example.feature_error.domain

import javax.inject.Inject

internal class ErrorUseCase @Inject constructor(private val repository: ErrorRepository) {
    fun checkConnection(): Boolean {
        return repository.checkConnection()
    }
}