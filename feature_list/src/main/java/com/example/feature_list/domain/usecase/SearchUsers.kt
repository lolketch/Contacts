package com.example.feature_list.domain.usecase

import com.example.api.UserDto
import javax.inject.Inject

class SearchUsers @Inject constructor() {

    fun onSearchTextChanged(
        searchText: String,
        listUsers: List<UserDto>
    ): List<UserDto> = listUsers.filter {
        it.firstName.lowercase().contains(searchText.lowercase())
                || it.lastName.lowercase().contains(searchText.lowercase())
                || it.position.lowercase().contains(searchText.lowercase())
    }
}