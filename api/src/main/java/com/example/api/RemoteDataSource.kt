package com.example.api

import io.reactivex.Single
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val usersApi: UsersApi) {
    fun fetchUsers(): Single<DataDto> = usersApi.fetchUsers()
}