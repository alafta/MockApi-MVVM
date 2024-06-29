package com.example.mockapiapplication.screen

import android.util.Log
import com.example.mockapiapplication.repository.TodoRepo


import androidx.lifecycle.*
import com.example.mockapiapplication.data.ToDoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TodoRepo
) : ViewModel() {

    val allItems: LiveData<List<ToDoItem>> = repository.getAllItems().asLiveData()

    fun addItem(item: ToDoItem) = viewModelScope.launch {
        repository.addItem(item)
    }

    fun updateItem(item: ToDoItem) = viewModelScope.launch {
        repository.updateItem(item)
    }

    fun deleteItem(item: ToDoItem) = viewModelScope.launch {
        repository.deleteItem(item)
    }

//    fun fetchAndStoreItems() = viewModelScope.launch {
//        val itemsFromApi = repository.fetchItemsFromApi()
//        repository.clearAllItems()
//        repository.insertItems(itemsFromApi)
//    }
fun fetchAndStoreItems() = viewModelScope.launch {
    if (repository.getItemCount() == 0) {
        try {
            val itemsFromApi = repository.fetchItemsFromApi()
            repository.clearAllItems()
            repository.insertItems(itemsFromApi)
        } catch (e: HttpException) {
            // Handle HTTP exception (e.g., show a message to the user)
            Log.e("HomeViewModel", "HTTP error: ${e.code()}")
        } catch (e: Exception) {
            // Handle other exceptions
            Log.e("HomeViewModel", "Error: ${e.message}")
        }
    }
}
}
