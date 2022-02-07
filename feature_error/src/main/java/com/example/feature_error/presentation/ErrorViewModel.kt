package com.example.feature_error.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.feature_error.domain.CheckConnection
import javax.inject.Inject

internal class ErrorViewModel @Inject constructor(private val checkConnection: CheckConnection) :
    ViewModel() {

    private val _connection = MutableLiveData<Boolean>()
    val connection: LiveData<Boolean> = _connection

    fun checkConnection() {
        _connection.postValue(checkConnection.checkConnection())
    }
}