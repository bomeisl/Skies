package com.example.skies.data.repositories

import com.example.skies.data.database.SkiesDatabase
import com.example.skies.data.network.Quote_network
import com.example.skies.data.network.QuotesDataSource
import javax.inject.Inject

class QuotesRepository @Inject constructor(skiesDatabase: SkiesDatabase) {

    suspend fun refreshDatabase(quotesDataSource: QuotesDataSource) {
        val networkQuotesFetch: List<Quote_network> = quotesDataSource.getDailyQuoteList()

    }

}