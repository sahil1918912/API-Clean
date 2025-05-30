package com.ahmedabad.api_clean.data.model

// UserResponse.kt (data/model for GET list users response)
data class UserResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>
)
