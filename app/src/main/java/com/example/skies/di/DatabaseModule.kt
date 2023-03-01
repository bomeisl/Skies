package com.example.skies.di

import android.content.Context
import androidx.room.Room
import com.example.skies.data.database.QuotesDao
import com.example.skies.data.database.SkiesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun providesQuotesDao(database: SkiesDatabase): QuotesDao {
        return database.quotesDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): SkiesDatabase {
        return Room.databaseBuilder(
            appContext,
            SkiesDatabase::class.java,
            "skies.db"
        ).build()
    }





}