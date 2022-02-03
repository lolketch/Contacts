package com.example.feature_host.domain

interface HostRepository {
    fun checkConnection(): Boolean
}