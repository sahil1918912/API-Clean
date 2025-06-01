// 12. Presentation Layer - UserScreen.kt (Improved)
package com.ahmedabad.api_clean.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmedabad.api_clean.domain.model.UserModel
import com.ahmedabad.api_clean.ui.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val uiState by userViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var isDialogOpen by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var editableUser by remember { mutableStateOf<UserModel?>(null) }
    var name by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }
    var roleError by remember { mutableStateOf<String?>(null) }

    // Show error snackbar
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            userViewModel.clearError()
        }
    }

    fun resetForm() {
        name = ""
        role = ""
        nameError = null
        roleError = null
        isEditing = false
        editableUser = null
    }

    fun validateForm(): Boolean {
        var isValid = true

        if (name.isBlank()) {
            nameError = "Name is required"
            isValid = false
        } else {
            nameError = null
        }

        if (role.isBlank()) {
            roleError = "Role is required"
            isValid = false
        } else {
            roleError = null
        }

        return isValid
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("User Management") },
                actions = {
                    IconButton(
                        onClick = { userViewModel.loadUsers() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    resetForm()
                    isDialogOpen = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add User"
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading && uiState.users.isEmpty() -> {
                    // Initial loading
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Loading users...")
                        }
                    }
                }

                uiState.users.isEmpty() -> {
                    // Empty state
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No users found",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Tap + to add your first user",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                else -> {
                    // Content with optional refresh loading
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (uiState.isRefreshing) {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }

                        items(
                            items = uiState.users,
                            key = { it.id }
                        ) { user ->
                            UserItem(
                                userModel = user,
                                onEdit = {
                                    name = user.name
                                    role = user.role
                                    isEditing = true
                                    editableUser = user
                                    isDialogOpen = true
                                },
                                onDelete = {
                                    userViewModel.deleteUser(user.id)
                                }
                            )
                        }
                    }
                }
            }
        }

        // Add/Edit Dialog
        if (isDialogOpen) {
            AlertDialog(
                onDismissRequest = {
                    isDialogOpen = false
                    resetForm()
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (validateForm()) {
                                if (isEditing && editableUser != null) {
                                    userViewModel.updateUser(
                                        editableUser!!.copy(
                                            name = name.trim(),
                                            role = role.trim()
                                        )
                                    )
                                } else {
                                    userViewModel.createUser(
                                        UserModel(
                                            name = name.trim(),
                                            role = role.trim()
                                        )
                                    )
                                }
                                isDialogOpen = false
                                resetForm()
                            }
                        }
                    ) {
                        Text(if (isEditing) "Update" else "Create")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            isDialogOpen = false
                            resetForm()
                        }
                    ) {
                        Text("Cancel")
                    }
                },
                title = {
                    Text(if (isEditing) "Edit User" else "Create User")
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                name = it
                                if (nameError != null) nameError = null
                            },
                            label = { Text("Name") },
                            isError = nameError != null,
                            supportingText = nameError?.let { { Text(it) } }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = role,
                            onValueChange = {
                                role = it
                                if (roleError != null) roleError = null
                            },
                            label = { Text("Role") },
                            isError = roleError != null,
                            supportingText = roleError?.let { { Text(it) } }
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun UserItem(
    userModel: UserModel,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = userModel.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = userModel.role,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onEdit() }
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onDelete() }
                )
            }
        }
    }
}