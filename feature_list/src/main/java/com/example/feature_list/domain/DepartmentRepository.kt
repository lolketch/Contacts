package com.example.feature_list.domain

import com.example.api.UserDto
import io.reactivex.Single

internal interface DepartmentRepository {
    fun fetchUsers(): Single<List<UserDto>>
}