package com.example.feature_error.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.api.ConnectResolver
import com.example.feature_error.domain.ErrorUseCase
import javax.inject.Inject
import javax.inject.Provider

internal class ErrorViewModel (private val errorUseCase: ErrorUseCase) :
    ViewModel() {

    private val _connection = MutableLiveData<Boolean>()
    val connection: LiveData<Boolean> = _connection

    fun checkConnection() {
        _connection.postValue(errorUseCase.checkConnection())
    }

    class Factory @Inject constructor(
        private val errorUseCase: Provider<ErrorUseCase>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            require(modelClass == ErrorViewModel::class.java)
            return ErrorViewModel(errorUseCase.get()) as T
        }
    }
}