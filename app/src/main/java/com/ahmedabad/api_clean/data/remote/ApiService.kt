package com.ahmedabad.api_clean.data.remote

import com.ahmedabad.api_clean.data.model.CreateUpdateUserRequest
import com.ahmedabad.api_clean.data.model.CreateUpdateUserResponse
import com.ahmedabad.api_clean.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int = 1): UserResponse

    @POST("users")
    suspend fun createUser(@Body user: CreateUpdateUserRequest): CreateUpdateUserResponse

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body user: CreateUpdateUserRequest
    ): CreateUpdateUserResponse

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>
}

