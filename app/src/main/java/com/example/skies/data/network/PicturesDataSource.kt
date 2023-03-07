package com.example.skies.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import javax.inject.Inject
import javax.inject.Singleton

data class Picture_network(
    val date: String,
    val time: String,
    val pic_url: String,
    val likes: Int
)

@Singleton
class PicturesDataSource @Inject constructor(private val ktorClient: HttpClient) {

    suspend fun getSkyPicturesNetwork(): List<Picture_network> =
        ktorClient.get("https://api.unsplash.com/search/photos?page=1&query=sky").body()

    suspend fun getSunsetPicturesNetwork(): List<Picture_network> =
        ktorClient.get("https://api.unsplash.com/search/photos?page=1&query=sunset").body()

    suspend fun getNightPicturesNetwork(): List<Picture_network> =
        ktorClient.get("https://api.unsplash.com/search/photos?page=1&query=night").body()

}