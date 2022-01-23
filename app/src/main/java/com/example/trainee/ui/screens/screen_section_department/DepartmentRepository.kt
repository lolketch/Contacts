package com.example.trainee.ui.screens.screen_department_host

import com.example.trainee.data.model.Users
import com.example.trainee.data.remote.RemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

interface DepartmentHostRepository {
    fun fetchUsers(): Single<Users>
}

class DepartmentHostRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    DepartmentHostRepository {
    override fun fetchUsers(): Single<Users> = remoteDataSource.fetchUsers()
}