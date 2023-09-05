package com.example.tranzmeotest.api

import com.example.tranzmeotest.data.model.User
import com.example.tranzmeotest.data.response.UsersResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("users")
    suspend fun getUsers(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): Response<UsersResponse>

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): Response<User>

}