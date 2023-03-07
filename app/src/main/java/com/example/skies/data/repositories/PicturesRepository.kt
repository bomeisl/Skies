package com.example.skies.data.repositories

import com.example.skies.data.database.PicturesDao
import com.example.skies.data.network.PicturesDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PicturesRepository @Inject constructor(
    private val picturesDataSource: PicturesDataSource,
    private val picturesDao: PicturesDao
    ) {



}