package com.ahmedabad.api_clean.data.model

// CreateUpdateUserResponse.kt (data/model for POST/PUT response)
data class CreateUpdateUserResponse(
    val name: String,
    val job: String,
    val id: String?,         // only for POST response
    val createdAt: String?,  // only for POST response
    val updatedAt: String?   // only for PUT response
)
