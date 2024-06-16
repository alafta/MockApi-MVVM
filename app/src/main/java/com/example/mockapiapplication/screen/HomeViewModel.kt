package com.example.mockapiapplication.screen

import com.example.mockapiapplication.repository.TodoRepo


import androidx.lifecycle.*
import com.example.mockapiapplication.data.ToDoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TodoRepo
) : ViewModel() {

    val allItems: LiveData<List<ToDoItem>> = repository.getAllItems().asLiveData()

    fun addItem(item: ToDoItem) = viewModelScope.launch {
        repository.insertItem(item)
    }

    fun updateItem(item: ToDoItem) = viewModelScope.launch {
        repository.updateItem(item)
    }

    fun deleteItem(item: ToDoItem) = viewModelScope.launch {
        repository.deleteItem(item)
    }

    fun fetchAndStoreItems() = viewModelScope.launch {
        // fetch from API and store in the local database
        val itemsFromApi = repository.fetchItemsFromApi()
        repository.clearAllItems()
        repository.insertItems(itemsFromApi)
    }
}
