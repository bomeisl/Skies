package com.example.skies.data.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Quotes")
data class Quotes_db(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("uid") val uid: Int,
    @ColumnInfo("quote") val quote: String,
    @ColumnInfo("author") val author: String
)

@Dao
interface QuotesDao{

    

}