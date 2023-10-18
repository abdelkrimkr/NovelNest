package com.example.novelnest.data

import com.example.novelnest.network.Item
import com.example.novelnest.network.NovelApiService

interface NovelRepository {
    suspend fun getNovel(searchName : String, maxResults: Int) : List<Item>
}

class NovelRepositoryImpl (private val novelApiService: NovelApiService) : NovelRepository{

    override suspend fun getNovel(searchName: String , maxResults: Int): List<Item> = novelApiService.getNovel(searchName, maxResults).items

}