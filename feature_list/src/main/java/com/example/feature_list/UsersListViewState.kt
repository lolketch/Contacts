package com.example.feature_list

import com.example.trainee.data.model.User

sealed class UsersListViewState {
    data class Success(val items: List<User>) : UsersListViewState()
    data class Error(val message: String) : UsersListViewState()
    object Loading : UsersListViewState()
}
