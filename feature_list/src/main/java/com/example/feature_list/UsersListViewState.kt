package com.example.feature_list

import com.example.api.UserDto

sealed class UsersListViewState {
    data class Success(val items: List<UserDto>) : UsersListViewState()
    data class Error(val message: String) : UsersListViewState()
    object Loading : UsersListViewState()
}
