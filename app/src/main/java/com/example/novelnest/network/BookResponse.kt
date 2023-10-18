package com.example.novelnest.network


data class BookResponse(
    val items: List<Item> ,
)

data class Item(
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val authors: List<String> ,
    val categories: List<String> ,
    val description: String ,
    val imageLinks: ImageLinks ,
    val infoLink: String ,
    val language: String ,
    val previewLink: String ,
    val publishedDate: String ,
    val publisher: String ,
    val title: String
)

data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String?
)