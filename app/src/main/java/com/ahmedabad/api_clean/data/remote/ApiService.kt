package com.ahmedabad.api_clean.data.remote

import com.ahmedabad.api_clean.data.dto.CreateUserDto
import com.ahmedabad.api_clean.data.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<UserDto>>

    @POST("users")
    suspend fun createUser(@Body user: CreateUserDto): Response<UserDto>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body user: CreateUserDto): Response<UserDto>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: String): Response<Unit>
}

