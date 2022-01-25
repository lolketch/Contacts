package com.example.trainee.utils

import com.example.trainee.data.model.User

sealed class UsersListViewState {
    data class Success(val items: List<User>) : UsersListViewState()
    data class Error(val message: String) : UsersListViewState()
    object Loading : UsersListViewState()
}
