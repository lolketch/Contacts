package com.example.feature_list.data

import com.example.trainee.data.model.Users
import com.example.api.RemoteDataSource
import com.example.feature_list.domain.DepartmentRepository
import io.reactivex.Single
import javax.inject.Inject

class DepartmentRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    DepartmentRepository {
    override fun fetchUsers(): Single<Users> = remoteDataSource.fetchUsers()
}