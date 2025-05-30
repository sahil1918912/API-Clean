package com.ahmedabad.api_clean.domain.repository

import com.ahmedabad.api_clean.data.model.CreateUpdateUserRequest
import com.ahmedabad.api_clean.data.model.CreateUpdateUserResponse
import com.ahmedabad.api_clean.data.model.UserResponse

interface UserRepository {
    suspend fun getUsers(page: Int): UserResponse
    suspend fun createUser(user: CreateUpdateUserRequest): CreateUpdateUserResponse
    suspend fun updateUser(id: Int, user: CreateUpdateUserRequest): CreateUpdateUserResponse
    suspend fun deleteUser(id: Int): Boolean
}
