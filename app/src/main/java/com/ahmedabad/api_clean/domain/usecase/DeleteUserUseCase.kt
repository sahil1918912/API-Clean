package com.ahmedabad.api_clean.domain.usecase

import com.ahmedabad.api_clean.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteUser(id)
}