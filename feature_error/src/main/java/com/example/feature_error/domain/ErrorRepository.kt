package com.example.feature_error.domain

interface ErrorRepository {
    fun checkConnection(): Boolean
}