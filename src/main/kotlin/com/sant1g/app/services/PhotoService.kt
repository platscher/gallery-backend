package com.sant1g.app.services

import com.sant1g.app.models.Photo
import com.sant1g.app.repositories.UnsplashRepository

class PhotoService(private val unsplashRepository: UnsplashRepository) {
    suspend fun getSinglePhoto(): Photo {
        val result = unsplashRepository.getRandomPhoto()
        return result
    }
}
