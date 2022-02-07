package com.example.feature_host.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.feature_host.domain.HostUseCase
import javax.inject.Inject

internal class DepartmentHostViewModel @Inject constructor(private val hostUseCase: HostUseCase) :
    ViewModel() {

    private val _connection = MutableLiveData<Boolean>()
    val connection: LiveData<Boolean> = _connection

    init {
        checkConnection()
    }

    private fun checkConnection() {
        _connection.postValue(hostUseCase.checkConnection())
    }

}