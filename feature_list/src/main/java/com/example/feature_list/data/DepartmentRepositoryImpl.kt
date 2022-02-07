package com.example.feature_list.data

import com.example.api.RemoteDataSource
import com.example.api.UserDto
import com.example.feature_list.domain.DepartmentRepository
import io.reactivex.Single
import javax.inject.Inject

internal class DepartmentRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    DepartmentRepository {
    override fun fetchUsers(): Single<List<UserDto>> =
        remoteDataSource.fetchUsers().map { data -> data.users }
}