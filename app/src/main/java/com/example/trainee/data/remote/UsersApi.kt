package com.example.trainee.data.remote

import com.example.trainee.data.model.Users
import io.reactivex.Single
import retrofit2.http.GET

interface UsersApi {

    @GET("users")
    fun fetchUsers(): Single<Users>
}