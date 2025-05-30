package com.ahmedabad.api_clean.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ahmedabad.api_clean.data.model.User
import com.ahmedabad.api_clean.ui.viewmodel.UserViewModel

@Composable
fun UserScreen(viewModel: UserViewModel = hiltViewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    var editingUser by remember { mutableStateOf<User?>(null) }
    var name by remember { mutableStateOf("") }
    var job by remember { mutableStateOf("") }

    // Load users on first launch
    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

    val users = viewModel.users
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        errorMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(users) { user ->
                UserCard(
                    user = user,
                    onEdit = {
                        editingUser = user
                        name = user.name ?: "${user.firstName.orEmpty()} ${user.lastName.orEmpty()}"
                        job = user.job.orEmpty()
                        showDialog = true
                    },
                    onDelete = {
                        user.id?.let { id ->
                            viewModel.deleteUser(id) { /* optionally show toast/snackbar */ }
                        }
                    }
                )
            }
        }

        Button(
            onClick = {
                editingUser = null
                name = ""
                job = ""
                showDialog = true
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Create User")
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(if (editingUser == null) "Create New User" else "Update User")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = job,
                        onValueChange = { job = it },
                        label = { Text("Job") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val userId = editingUser?.id
                    if (userId != null) {
                        viewModel.updateUser(userId, name, job) { success ->
                            if (success) {
                                showDialog = false
                                editingUser = null
                            }
                        }
                    } else {
                        viewModel.createUserAndAddToList(name, job) { success ->
                            if (success) showDialog = false
                        }
                    }
                }) {
                    Text(if (editingUser == null) "Create" else "Update")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun UserCard(user: User, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user.avatar ?: "",
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = listOfNotNull(
                        user.firstName,
                        user.lastName,
                        user.name
                    ).joinToString(" "),
                    style = MaterialTheme.typography.titleMedium
                )
                user.email?.let { Text(it) }
                user.job?.let {
                    Text(it, style = MaterialTheme.typography.bodySmall)
                }
            }

            Row {
                TextButton(onClick = onEdit) {
                    Text("Edit")
                }
                TextButton(onClick = onDelete) {
                    Text("Delete")
                }
            }
        }
    }
}
