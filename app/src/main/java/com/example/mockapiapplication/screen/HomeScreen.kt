package com.example.mockapiapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mockapiapplication.data.ToDoItem

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val todoItems by viewModel.allItems.observeAsState(initial = emptyList())
    var showEditDialog by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var currentTodo by remember { mutableStateOf<ToDoItem?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Todo List") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showAddDialog = true
            }) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = androidx.compose.material3.MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                items(todoItems) { todoItem ->
                    TodoItemRow(todoItem, viewModel::deleteItem, onEdit = {
                        currentTodo = it
                        showEditDialog = true
                    })
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchAndStoreItems()
    }

    if (showEditDialog) {
        currentTodo?.let { todo ->
            EditTodoDialog(
                todoItem = todo,
                onDismiss = { showEditDialog = false },
                onSave = { updatedTodo ->
                    viewModel.updateItem(updatedTodo)
                    showEditDialog = false
                }
            )
        }
    }

    if (showAddDialog) {
        AddTodoDialog(
            onDismiss = { showAddDialog = false },
            onSave = { newTodo ->
                viewModel.addItem(newTodo)
                showAddDialog = false
            }
        )
    }
}


@Composable
fun TodoItemRow(todoItem: ToDoItem, onDelete: (ToDoItem) -> Unit, onEdit: (ToDoItem) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val (title, description, editButton, deleteButton) = createRefs()

            Text(
                text = todoItem.title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(editButton.start, margin = 8.dp)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = todoItem.description,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.constrainAs(description) {
                    top.linkTo(title.bottom, margin = 4.dp)
                    start.linkTo(parent.start)
                    end.linkTo(editButton.start, margin = 8.dp)
                    width = androidx.constraintlayout.compose.Dimension.fillToConstraints
                },
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(
                onClick = { onEdit(todoItem) },
                modifier = Modifier.constrainAs(editButton) {
                    top.linkTo(parent.top)
                    end.linkTo(deleteButton.start, margin = 8.dp)
                }
            ) {
                Icon(Icons.Filled.Edit, contentDescription = null)
            }

            IconButton(
                onClick = { onDelete(todoItem) },
                modifier = Modifier.constrainAs(deleteButton) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            ) {
                Icon(Icons.Filled.Delete, contentDescription = null)
            }
        }
    }
}



@Composable
fun EditTodoDialog(
    todoItem: ToDoItem,
    onDismiss: () -> Unit,
    onSave: (ToDoItem) -> Unit
) {
    var title by remember { mutableStateOf(todoItem.title) }
    var description by remember { mutableStateOf(todoItem.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Todo") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(todoItem.copy(title = title, description = description))
                onDismiss()
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    onSave: (ToDoItem) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Todo") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank() && description.isNotBlank()) {
                    onSave(ToDoItem(0, title, description))
                    onDismiss()
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
