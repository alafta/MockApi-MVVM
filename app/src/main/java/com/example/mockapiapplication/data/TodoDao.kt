package com.example.mockapiapplication.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {


    @Query("SELECT * FROM todoitem")
    fun getAll(): Flow<List<ToDoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ToDoItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: kotlin.Unit)

    @Update
    suspend fun update(item: ToDoItem)

    @Delete
    suspend fun delete(item: ToDoItem)

    @Query("DELETE FROM todoitem")
    suspend fun deleteAll()
}
