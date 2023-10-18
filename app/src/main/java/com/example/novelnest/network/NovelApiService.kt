package com.example.novelnest.network

import retrofit2.http.GET
import retrofit2.http.Query

interface NovelApiService {

    @GET("volumes")
    suspend fun getNovel(
        @Query("q") searchName : String ,
        @Query("maxResults") results: Int
    ): BookResponse
}