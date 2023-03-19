package com.sant1g.app.clients.unsplash

import com.sant1g.app.clients.unsplash.dto.CollectionPhoto
import com.sant1g.app.clients.unsplash.dto.PhotoResponse
import com.sant1g.app.configuration.Config
import com.sant1g.app.configuration.get
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.*
import io.ktor.http.*
import java.lang.Exception

class UnsplashClient(private val config: Config) {

  private val client = createClient()

  fun get(): HttpClient {
    return client
  }

  suspend fun getCollection(collectionId: String): List<CollectionPhoto> {
    return client.get("$HOST/collections/$collectionId/photos") {
      url {
        parameters.append(PARAM_ORIENTATION_KEY, PARAM_ORIENTATION_VALUE)
        parameters.append(PARAM_PER_PAGE_KEY, PARAM_PER_PAGE_VALUE)
      }
      headers {
        append(HttpHeaders.Authorization, getAuthHeader())
      }
    }
  }

  suspend fun getPhoto(photoId: String): PhotoResponse {
    return try {
      client.get("$HOST/photos/$photoId") {
        headers {
          append(HttpHeaders.Authorization, getAuthHeader())
        }
      }
    } catch (e: Exception) {
      println(e)

      throw e
    }
  }

  private fun createClient(): HttpClient {
    return HttpClient(Apache) {
      engine {
        socketTimeout = config.get("client.unsplash.socketTimeout")
        connectTimeout = config.get("client.unsplash.connectTimeout")
        connectionRequestTimeout = config.get("client.unsplash.connectionRequestTimeout")
        customizeClient {
          setMaxConnTotal(config.get("client.unsplash.apache.maxConnTotal"))
          setMaxConnPerRoute(config.get("client.unsplash.apache.maxConnPerRoute"))
        }
      }
      install(JsonFeature) {
        serializer = JacksonSerializer()
      }
    }
  }

  private fun getAuthHeader(): String {
    return "Client-ID ${config.get<String>("client.unsplash.authToken")}"
  }

  companion object {
    private const val HOST = "https://api.unsplash.com"
    private const val PARAM_ORIENTATION_KEY = "orientation"
    private const val PARAM_ORIENTATION_VALUE = "landscape"
    private const val PARAM_PER_PAGE_KEY = "per_page"
    private const val PARAM_PER_PAGE_VALUE = "1000"
  }
}
