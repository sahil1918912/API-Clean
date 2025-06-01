package com.ahmedabad.api_clean.domain.usecase

import com.ahmedabad.api_clean.domain.model.UserModel
import com.ahmedabad.api_clean.domain.repository.UserRepository
import com.ahmedabad.api_clean.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCases @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun getUsers(): Flow<Resource<List<UserModel>>> {
        return repository.getUsers()
    }

    suspend fun createUser(user: UserModel): Resource<UserModel> {
        return repository.createUser(user)
    }

    suspend fun updateUser(user: UserModel): Resource<UserModel> {
        return repository.updateUser(user)
    }

    suspend fun deleteUser(id: String): Resource<Unit> {
        return repository.deleteUser(id)
    }
}