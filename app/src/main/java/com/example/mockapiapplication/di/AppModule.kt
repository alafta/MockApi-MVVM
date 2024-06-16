package com.example.mockapiapplication.di

import android.content.Context
import androidx.room.Room
import com.example.mockapiapplication.data.ApiService
import com.example.mockapiapplication.data.AppDb
import com.example.mockapiapplication.data.TodoDao
import com.example.mockapiapplication.repository.TodoRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDb {
        return Room.databaseBuilder(
            context,
            AppDb::class.java,
            "todo_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTodoDao(db: AppDb): TodoDao {
        return db.todoItemDao()
    }

    @Provides
    fun provideTodoRepo(dao: TodoDao, apiService: ApiService): TodoRepo {
        return TodoRepo(dao, apiService)
    }
}


