package com.ahmedabad.api_clean.data.repository

import com.ahmedabad.api_clean.data.remote.ApiService
import com.ahmedabad.api_clean.domain.model.User
import com.ahmedabad.api_clean.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(val api: ApiService) : UserRepository {
    override suspend fun getUsers(): List<User> {
        return api.getUsers()
    }

}