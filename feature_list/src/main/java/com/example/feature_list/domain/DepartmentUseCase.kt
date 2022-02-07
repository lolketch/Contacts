package com.example.feature_list.domain

import com.example.api.UserDto
import javax.inject.Inject

internal class DepartmentUseCase @Inject constructor(private val repository: DepartmentRepository) {
    fun fetchUsers() = repository.fetchUsers()

    fun onSearchTextChanged(
        searchText: String,
        listUsers: List<UserDto>
    ): List<UserDto> = listUsers.filter {
        it.firstName.lowercase().contains(searchText.lowercase())
                || it.lastName.lowercase().contains(searchText.lowercase())
                || it.position.lowercase().contains(searchText.lowercase())
    }
}