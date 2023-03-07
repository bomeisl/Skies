package com.example.skies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.BodyProgress.Plugin.install
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun ProvidesKtorEngine(): HttpClientEngine {
        return CIO.create()
    }

    @Singleton
    @Provides
    fun ProvidesKtorClient(ktorEngine: HttpClientEngine): HttpClient {
        return HttpClient(ktorEngine) {
            install(ContentNegotiation) {
                gson()
            }
        }
    }



}



