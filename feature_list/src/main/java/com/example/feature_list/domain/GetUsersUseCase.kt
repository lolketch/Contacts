package com.example.feature_list.domain

import com.example.feature_list.data.DepartmentRepositoryImpl
import javax.inject.Inject

internal class GetUsersUseCase @Inject constructor(private val repository: DepartmentRepository) {
    fun fetchUsers() = repository.fetchUsers()
}