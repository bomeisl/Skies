package com.example.skies.data.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "Productivity")
data class Productivity_db(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "score") val score: Int = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "task") val task: Int
)

@Dao
interface ProductivityDao{

    @Query("SELECT * FROM Productivity WHERE task = :task")
    fun getTaskProductivity(task: Int): Flow<List<Productivity_db>>

    @Upsert
    suspend fun upsertProductivity(productivity: Productivity_db)

    @Delete
    suspend fun deleteProductivity(productivity: Productivity_db)


}