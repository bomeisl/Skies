package com.example.skies.data.repositories

import com.example.skies.data.database.PicturesDao
import com.example.skies.data.network.Picture_network
import com.example.skies.data.network.PicturesDataSource
import com.example.skies.data.network.toDB
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PicturesRepository @Inject constructor(
    private val picturesDataSource: PicturesDataSource,
    private val picturesDao: PicturesDao
    ) {

    suspend fun refreshDatabase() {
        val networkSkyPictures = picturesDataSource.getSkyPicturesNetwork()
        for (picture in networkSkyPictures) {
            picturesDao.upsertPicture(picture.toDB())
        }
    }

    suspend fun getDBPicsbySearchTerm() {

    }


}