package com.example.trainee.ui.screens.screen_department_host

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trainee.utils.ConnectResolver
import javax.inject.Inject

class DepartmentHostViewModel @Inject constructor(private val connectResolver: ConnectResolver) :
    ViewModel() {
    private val _connection = MutableLiveData<Boolean>()
    val connection: LiveData<Boolean> = _connection

    init {
        checkConnection()
    }

    private fun checkConnection() {
        if (connectResolver.isOnline()) {
            _connection.postValue(true)
        } else _connection.postValue(false)
    }
}