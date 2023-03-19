package com.sant1g.app.repositories

import com.sant1g.app.clients.unsplash.UnsplashClient
import com.sant1g.app.models.Photo
import org.slf4j.LoggerFactory

class UnsplashRepository(private val unsplashClient: UnsplashClient) {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private var photoCollection: List<String> = emptyList()

    suspend fun init() {
        photoCollection = getCollection()
    }

    suspend fun getRandomPhoto(): Photo {
        val id = photoCollection.shuffled().first()

        return unsplashClient.getPhoto(id).toDomain()
    }

    private suspend fun getCollection(): List<String> {
        logger.info("Fetching collection $COLLECTION_ID information...")
        return unsplashClient.getCollection(COLLECTION_ID).map { it.id }
    }

    companion object {
        private const val COLLECTION_ID = "11649432"
    }
}
