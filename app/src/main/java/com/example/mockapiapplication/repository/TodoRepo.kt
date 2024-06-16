package com.example.mockapiapplication.repository

import com.example.mockapiapplication.data.ApiService
import com.example.mockapiapplication.data.ToDoItem
import com.example.mockapiapplication.data.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepo @Inject constructor(
    private val dao: TodoDao,
    private val apiService: ApiService
) {

    fun getAllItems() = dao.getAll()

    suspend fun insertItem(item: ToDoItem) {
        dao.insert(item)
    }

    suspend fun updateItem(item: ToDoItem) {
        dao.update(item)
    }

    suspend fun deleteItem(item: ToDoItem) {
        dao.delete(item)
    }

    suspend fun clearAllItems() {
        dao.deleteAll()
    }

    suspend fun insertItems(items: Unit) {
        dao.insertAll(items)
    }

    suspend fun fetchItemsFromApi() {
        return withContext(Dispatchers.IO) {
            // Make API call to fetch items
            apiService.getTodoItems()
        }
    }
}


