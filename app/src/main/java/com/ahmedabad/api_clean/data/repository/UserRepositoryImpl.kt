package com.ahmedabad.api_clean.data.repository

import com.ahmedabad.api_clean.data.model.CreateUpdateUserRequest
import com.ahmedabad.api_clean.data.model.CreateUpdateUserResponse
import com.ahmedabad.api_clean.data.model.UserResponse
import com.ahmedabad.api_clean.data.remote.ApiService
import com.ahmedabad.api_clean.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUsers(page: Int): UserResponse {
        return apiService.getUsers(page)
    }

    override suspend fun createUser(user: CreateUpdateUserRequest): CreateUpdateUserResponse {
        return apiService.createUser(user)
    }

    override suspend fun updateUser(id: Int, user: CreateUpdateUserRequest): CreateUpdateUserResponse {
        return apiService.updateUser(id, user)
    }

    override suspend fun deleteUser(id: Int): Boolean {
        val response = apiService.deleteUser(id)
        return response.isSuccessful
    }
}
