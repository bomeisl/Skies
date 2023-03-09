package com.example.skies.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.skies.data.database.PicturesDao
import com.example.skies.data.repositories.PicturesRepository
import javax.inject.Inject

class PicturesViewModel @Inject constructor(
    private val picturesRepository: PicturesRepository
): ViewModel() {



}