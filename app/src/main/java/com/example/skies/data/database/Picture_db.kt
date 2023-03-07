package com.example.skies.data.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Entity(tableName = "Pictures")
data class Picture_db(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val id: Int,
    @ColumnInfo("date") val date: String,
    @ColumnInfo("time") val time: String,
    @ColumnInfo("pic_url") val pic_url: String,
    @ColumnInfo("likes") val likes: Int
)

@Dao
interface PicturesDao {

    @Query("SELECT * FROM Pictures WHERE :date = date")
    suspend fun pullPictureByDate(date: String): Flow<List<Picture_db>>

    @Upsert
    suspend fun upsertPicture(pictureDb: Picture_db)

    @Delete
    suspend fun deletePicture(pictureDb: Picture_db)

}

