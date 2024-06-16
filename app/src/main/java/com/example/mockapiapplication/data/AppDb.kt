package com.example.mockapiapplication.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [ToDoItem::class], version = 2)
abstract class AppDb : RoomDatabase() {
    abstract fun todoItemDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDb? = null

        fun getDatabase(context: Context): AppDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "todo_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}



