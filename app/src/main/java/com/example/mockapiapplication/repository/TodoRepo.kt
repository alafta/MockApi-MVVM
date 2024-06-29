package com.example.mockapiapplication.repository

import com.example.mockapiapplication.data.ApiService
import com.example.mockapiapplication.data.ToDoItem
import com.example.mockapiapplication.data.TodoDao
import javax.inject.Inject

class TodoRepo @Inject constructor(
    private val dao: TodoDao,
    private val apiService: ApiService
) {

    fun getAllItems() = dao.getAll()

    suspend fun addItem(item: ToDoItem) {
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

    suspend fun insertItems(items: List<ToDoItem>) {
        dao.insertAll(*items.toTypedArray())
    }

    suspend fun fetchItemsFromApi(): List<ToDoItem> {
        return apiService.fetchTodoItems()
    }

    suspend fun getItemCount(): Int{
        return dao.getItemCount()
    }
}


