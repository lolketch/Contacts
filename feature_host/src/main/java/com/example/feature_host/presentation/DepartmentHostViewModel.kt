package com.example.feature_host.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.api.ConnectResolver
import com.example.feature_host.domain.HostUseCase
import javax.inject.Inject
import javax.inject.Provider

internal class DepartmentHostViewModel(private val hostUseCase: HostUseCase) :
    ViewModel() {

    private val _connection = MutableLiveData<Boolean>()
    val connection: LiveData<Boolean> = _connection

    init {
        checkConnection()
    }

    private fun checkConnection() {
        _connection.postValue(hostUseCase.checkConnection())
    }

    class Factory @Inject constructor(
        private val hostUseCase: Provider<HostUseCase>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == DepartmentHostViewModel::class.java)
            return DepartmentHostViewModel(hostUseCase.get()) as T
        }
    }
}