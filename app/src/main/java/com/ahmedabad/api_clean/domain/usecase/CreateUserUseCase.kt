package com.ahmedabad.api_clean.domain.usecase

import com.ahmedabad.api_clean.data.model.CreateUpdateUserRequest
import com.ahmedabad.api_clean.domain.repository.UserRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: CreateUpdateUserRequest) = repository.createUser(user)
}