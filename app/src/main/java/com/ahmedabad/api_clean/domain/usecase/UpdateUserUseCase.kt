package com.ahmedabad.api_clean.domain.usecase

import com.ahmedabad.api_clean.data.model.CreateUpdateUserRequest
import com.ahmedabad.api_clean.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int, user: CreateUpdateUserRequest) = repository.updateUser(id, user)
}