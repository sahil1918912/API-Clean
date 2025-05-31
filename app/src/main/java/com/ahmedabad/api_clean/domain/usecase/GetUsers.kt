package com.ahmedabad.api_clean.domain.usecase

import com.ahmedabad.api_clean.domain.model.User
import com.ahmedabad.api_clean.domain.repository.UserRepository
import javax.inject.Inject

class GetUsers @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(): List<User> {
        return repository.getUsers()
    }
}