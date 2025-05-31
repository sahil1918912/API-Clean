package com.ahmedabad.api_clean.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedabad.api_clean.domain.model.User
import com.ahmedabad.api_clean.domain.usecase.GetUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val getUsers: GetUsers) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val user : StateFlow<List<User>> = _users

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                _users.value = getUsers()
                Log.e("Get_Users", "fetchUsers: ${_users.value}", )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}