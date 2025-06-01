package com.ahmedabad.api_clean.data.repository

import com.ahmedabad.api_clean.data.mapper.toCreateUserDto
import com.ahmedabad.api_clean.data.mapper.toUserModel
import com.ahmedabad.api_clean.data.remote.ApiService
import com.ahmedabad.api_clean.domain.model.UserModel
import com.ahmedabad.api_clean.domain.repository.UserRepository
import com.ahmedabad.api_clean.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ApiService
) : UserRepository {

    override suspend fun getUsers(): Flow<Resource<List<UserModel>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getUsers()
            if (response.isSuccessful) {
                val users = response.body()?.map { it.toUserModel() } ?: emptyList()
                emit(Resource.Success(users))
            } else {
                emit(Resource.Error("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        }
    }

    override suspend fun createUser(user: UserModel): Resource<UserModel> {
        return try {
            val response = api.createUser(user.toCreateUserDto())
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it.toUserModel())
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun updateUser(user: UserModel): Resource<UserModel> {
        return try {
            val response = api.updateUser(user.id, user.toCreateUserDto())
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it.toUserModel())
                } ?: Resource.Error("Empty response body")
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun deleteUser(id: String): Resource<Unit> {
        return try {
            val response = api.deleteUser(id)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }
}