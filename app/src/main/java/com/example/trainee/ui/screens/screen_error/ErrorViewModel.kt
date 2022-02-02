package com.example.trainee.ui.screens.screen_error

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trainee.utils.ConnectResolver
import javax.inject.Inject

class ErrorViewModel @Inject constructor(private val connectResolver: ConnectResolver) :
    ViewModel() {

    private val _connection = MutableLiveData<Boolean>()
    val connection: LiveData<Boolean> = _connection

    fun checkConnection() {
        _connection.postValue(connectResolver.isOnline())
    }
}