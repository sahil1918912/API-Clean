package com.ahmedabad.api_clean.domain.usecase

import com.ahmedabad.api_clean.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(page: Int) = repository.getUsers(page)
}