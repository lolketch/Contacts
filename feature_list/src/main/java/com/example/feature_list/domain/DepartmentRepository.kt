package com.example.feature_list.domain

import com.example.trainee.data.model.Users
import io.reactivex.Single

internal interface DepartmentRepository {
    fun fetchUsers(): Single<Users>
}