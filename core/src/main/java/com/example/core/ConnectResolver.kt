package com.example.core

import android.net.ConnectivityManager
import android.os.Build
import javax.inject.Inject

class ConnectResolver @Inject constructor(private val connectivityManager: ConnectivityManager) {
    fun isOnline(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.activeNetwork != null
        } else {
            connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}