package com.example.skies.data.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "Quotes")
data class Quote_db(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val id: Int,
    @ColumnInfo("quote") val quote: String,
    @ColumnInfo("author") val author: String,
    @ColumnInfo("date") val date: String,
    @ColumnInfo("time") val time: String
)

@Dao
interface QuotesDao{

    @Query("SELECT * FROM Quotes WHERE quote = quote")
    fun pullQuote(): Flow<List<Quote_db>>

    @Query("SELECT * FROM Quotes WHERE date = date")
    fun pullDailyQuotes(): Flow<List<Quote_db>>

    @Upsert
    suspend fun upsertQuote(quote: Quote_db)

    @Delete
    suspend fun deleteQuote(quote: Quote_db)

}