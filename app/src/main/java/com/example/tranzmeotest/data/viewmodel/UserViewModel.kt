package com.example.tranzmeotest.data.viewmodel

import androidx.lifecycle.*
import com.example.tranzmeotest.api.ResultResponse
import com.example.tranzmeotest.data.model.User
import com.example.tranzmeotest.data.repository.UserRepository
import com.example.tranzmeotest.data.response.UsersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor (private val repository: UserRepository) : ViewModel() {

    fun getUsers(limit: Int, skip: Int): LiveData<ResultResponse<Response<UsersResponse>>> =
        liveData {
            var responseGet: Response<UsersResponse>? = null
            kotlin.runCatching {
                responseGet = repository.getUsers(limit, skip)
            }.onSuccess {
                when (responseGet?.code()) {
                    200 -> {
                        emit(ResultResponse.success(responseGet!!))
                    }
                    else -> {
                        emit(ResultResponse.error("Error occurred", responseGet))
                    }
                }
            }.onFailure {
                emit(ResultResponse.error("Failed", responseGet))
            }
        }

    fun getUserById(id: Int): LiveData<ResultResponse<Response<User>>> = liveData {
        var responseGet: Response<User>? = null
        kotlin.runCatching {
            responseGet = repository.getUserById(id)
        }.onSuccess {
            when (responseGet?.code()) {
                200 -> {
                    emit(ResultResponse.success(responseGet!!))
                }
                else -> {
                    emit(ResultResponse.error("Error occurred", responseGet))
                }
            }
        }.onFailure {
            emit(ResultResponse.error("Failed", responseGet))
        }
    }
}