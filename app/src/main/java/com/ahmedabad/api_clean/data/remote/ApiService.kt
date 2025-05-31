package com.ahmedabad.api_clean.data.remote

import com.ahmedabad.api_clean.domain.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

}

