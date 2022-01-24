package com.example.trainee.data.remote

import com.example.trainee.data.model.Users
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface UsersApi {

    @GET("feac7ea4-b72c-4d54-9350-c652a929631f")
    fun fetchUsers(): Single<Users>
}