package com.example.trainee.data.remote

import com.example.trainee.data.model.Users
import io.reactivex.Single
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val usersApi: UsersApi) {
    fun fetchUsers(): Single<Users> = usersApi.fetchUsers()
}