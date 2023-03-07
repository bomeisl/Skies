package com.example.skies.data.repositories

import com.example.skies.data.database.QuotesDao
import com.example.skies.data.database.SkiesDatabase
import com.example.skies.data.network.Quote_network
import com.example.skies.data.network.QuotesDataSource
import com.example.skies.data.network.toDB
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuotesRepository @Inject constructor(
    private val quotesDataSource: QuotesDataSource,
    private val quotesDao: QuotesDao
) {

    suspend fun refreshDatabase(quotesDataSource: QuotesDataSource) {
        val networkQuotesFetch: List<Quote_network> = quotesDataSource.getDailyQuoteListNetwork()
        networkQuotesFetch.forEach {
            quotesDao.upsertQuote(it.toDB())
        }
    }

    suspend fun dbGrabDailyQuotes(quotesDao: QuotesDao) = quotesDao.pullDailyQuotes()

}