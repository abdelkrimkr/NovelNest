package com.example.novelnest.data

import com.example.novelnest.network.NovelApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val novelRepository : NovelRepository
}


class DefaultContainer () : AppContainer{

    private val baseUrl = "https://www.googleapis.com/books/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService : NovelApiService by lazy {
        retrofit.create(NovelApiService::class.java)
    }

    override val novelRepository: NovelRepository by lazy {
        NovelRepositoryImpl(retrofitService)
    }
}