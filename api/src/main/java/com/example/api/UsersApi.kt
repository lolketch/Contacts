package com.example.api

import io.reactivex.Single
import retrofit2.http.GET

interface UsersApi {

    @GET("feac7ea4-b72c-4d54-9350-c652a929631f")
    fun fetchUsers(): Single<DataDto>
}