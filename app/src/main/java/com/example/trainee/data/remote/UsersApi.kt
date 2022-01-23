package com.example.trainee.data.remote

import com.example.trainee.data.model.Users
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface UsersApi {

    @GET("users")
    fun fetchUsers(): Single<Users>
}