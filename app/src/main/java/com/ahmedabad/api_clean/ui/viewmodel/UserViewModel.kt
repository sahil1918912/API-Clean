package com.ahmedabad.api_clean.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedabad.api_clean.domain.model.UserModel
import com.ahmedabad.api_clean.domain.usecase.UserUseCases
import com.ahmedabad.api_clean.ui.state.UserUiState
import com.ahmedabad.api_clean.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            userUseCases.getUsers().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true,
                            error = null
                        )
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            users = resource.data ?: emptyList(),
                            isLoading = false,
                            error = null,
                            isRefreshing = false
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = resource.message,
                            isRefreshing = false
                        )
                    }
                }
            }
        }
    }

    fun createUser(user: UserModel) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            when (val result = userUseCases.createUser(user)) {
                is Resource.Success -> {
                    loadUsers() // Refresh the list
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        error = result.message,
                        isRefreshing = false
                    )
                }
                is Resource.Loading -> {
                    // Handle loading if needed
                }
            }
        }
    }

    fun updateUser(user: UserModel) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            when (val result = userUseCases.updateUser(user)) {
                is Resource.Success -> {
                    loadUsers() // Refresh the list
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        error = result.message,
                        isRefreshing = false
                    )
                }
                is Resource.Loading -> {
                    // Handle loading if needed
                }
            }
        }
    }

    fun deleteUser(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            when (val result = userUseCases.deleteUser(id)) {
                is Resource.Success -> {
                    loadUsers() // Refresh the list
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        error = result.message,
                        isRefreshing = false
                    )
                }
                is Resource.Loading -> {
                    // Handle loading if needed
                }
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}