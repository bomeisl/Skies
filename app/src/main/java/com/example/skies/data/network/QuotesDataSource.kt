package com.example.skies.data.network

import com.example.skies.data.database.Quote_db
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

data class Quote_network(
    var quote: String,
    var author: String,
    var date: String,
    var time: String
)

fun Quote_network.toDB(): Quote_db = Quote_db(
    id = 0,
    quote = quote,
    author = author,
    date = date,
    time = time
)

@Singleton
class QuotesDataSource @Inject constructor(private val ktorClient: HttpClient) {

    suspend fun getDailyQuoteListNetwork(): List<Quote_network> =
        ktorClient.get("https://zenquotes.io/api/quotes").body()

}