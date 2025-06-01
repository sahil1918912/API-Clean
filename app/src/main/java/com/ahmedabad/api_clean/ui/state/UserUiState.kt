package com.ahmedabad.api_clean.ui.state

import com.ahmedabad.api_clean.domain.model.UserModel

data class UserUiState(
    val users: List<UserModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)