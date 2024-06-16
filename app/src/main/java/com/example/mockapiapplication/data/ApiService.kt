package com.example.mockapiapplication.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("v3/506bed6a-6a75-442f-84c0-79fedcf56ef2")
    suspend fun fetchTodoItems(): List<ToDoItem>
    fun getTodoItems()
}

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
