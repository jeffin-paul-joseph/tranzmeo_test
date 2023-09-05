package com.example.tranzmeotest.data.repository

import com.example.tranzmeotest.api.ApiClient
import com.example.tranzmeotest.api.ApiService
import com.example.tranzmeotest.data.model.User
import com.example.tranzmeotest.data.response.UsersResponse
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private var apiService: ApiService) {

    suspend fun getUsers(limit: Int, skip: Int): Response<UsersResponse> {
        return apiService.getUsers(limit, skip)
    }
    suspend fun getUserById(id: Int): Response<User> {
        return apiService.getUserById(id)
    }
}