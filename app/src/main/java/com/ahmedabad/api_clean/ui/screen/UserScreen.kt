package com.ahmedabad.api_clean.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmedabad.api_clean.domain.model.User
import com.ahmedabad.api_clean.ui.viewmodel.UserViewModel

@Composable
fun UserScreen(modifier: Modifier,viewModel: UserViewModel = hiltViewModel()) {
    val userList by viewModel.user.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        if (userList.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(userList) { user ->
                    UserItem(user = user)
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Name: ${user.name}", style = MaterialTheme.typography.titleMedium)
        Text(text = "Role: ${user.role}", style = MaterialTheme.typography.bodyMedium)
//        Text(text = "ID: ${user._id}", style = MaterialTheme.typography.bodySmall)
    }
}

