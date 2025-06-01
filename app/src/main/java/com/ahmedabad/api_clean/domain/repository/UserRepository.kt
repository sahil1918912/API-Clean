package com.ahmedabad.api_clean.domain.repository

import com.ahmedabad.api_clean.domain.model.UserModel
import com.ahmedabad.api_clean.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<Resource<List<UserModel>>>
    suspend fun createUser(user: UserModel): Resource<UserModel>
    suspend fun updateUser(user: UserModel): Resource<UserModel>
    suspend fun deleteUser(id: String): Resource<Unit>
}
