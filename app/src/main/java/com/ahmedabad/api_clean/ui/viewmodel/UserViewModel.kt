package com.ahmedabad.api_clean.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedabad.api_clean.data.model.CreateUpdateUserRequest
import com.ahmedabad.api_clean.data.model.User
import com.ahmedabad.api_clean.domain.usecase.GetUsersUseCase
import com.ahmedabad.api_clean.domain.usecase.CreateUserUseCase
import com.ahmedabad.api_clean.domain.usecase.DeleteUserUseCase
import com.ahmedabad.api_clean.domain.usecase.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    var users by mutableStateOf<List<User>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadUsers(page: Int = 1) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = getUsersUseCase(page)
                Log.d("UserViewModel", "Loaded users: ${response.data}")
                users = response.data
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "Error fetching users"
                Log.e("UserViewModel", "Error: ${e.localizedMessage}")
            }
            isLoading = false
        }
    }

    fun createUserAndAddToList(name: String, job: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = createUserUseCase(CreateUpdateUserRequest(name, job))
                Log.d("UserViewModel", "User created: $response")

                val newUser = User(
                    id = response.id?.toIntOrNull(),
                    email = "${name.lowercase().replace(" ", ".")}@demo.com",
                    firstName = name.split(" ").firstOrNull(),
                    lastName = name.split(" ").getOrNull(1) ?: "",
                    avatar = "https://reqres.in/img/faces/1-image.jpg",
                    name = name,
                    job = job
                )
                users = users + newUser
                onResult(true)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
                Log.e("UserViewModel", "Create Error: ${e.localizedMessage}")
                onResult(false)
            }
        }
    }

    fun updateUser(id: Int, name: String, job: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                updateUserUseCase(id, CreateUpdateUserRequest(name, job))
                // Manually update the user in the list
                users = users.map { user ->
                    if (user.id == id) {
                        user.copy(name = name, job = job)
                    } else user
                }
                Log.d("UserViewModel", "User updated: id=$id, name=$name, job=$job")
                onResult(true)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
                Log.e("UserViewModel", "Update Error: ${e.localizedMessage}")
                onResult(false)
            }
        }
    }

    fun deleteUser(id: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val success = deleteUserUseCase(id)
                if (success) {
                    users = users.filterNot { it.id == id }
                    Log.d("UserViewModel", "User deleted: id=$id")
                }
                onResult(success)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
                Log.e("UserViewModel", "Delete Error: ${e.localizedMessage}")
                onResult(false)
            }
        }
    }
}

