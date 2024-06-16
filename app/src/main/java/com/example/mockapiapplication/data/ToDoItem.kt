package com.example.mockapiapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoitem")
data class ToDoItem(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String
)
