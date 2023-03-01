package com.example.skies.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.time.LocalDate
import java.util.Date

data class Quote_network(
    var quote: String,
    var author: String,
)

class QuotesDataSource(val ktorClient: HttpClient) {

    suspend fun getDailyQuoteList(): List<Quote_network> =
        ktorClient.get("https://zenquotes.io/api/quotes").body()


}