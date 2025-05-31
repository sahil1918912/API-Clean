package com.ahmedabad.api_clean.domain.repository

import com.ahmedabad.api_clean.domain.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>

}
