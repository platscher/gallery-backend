package com.sant1g.app.clients.unsplash.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.sant1g.app.models.ExifInformation
import com.sant1g.app.models.Location
import com.sant1g.app.models.Photo
import com.sant1g.app.models.User

@JsonIgnoreProperties(ignoreUnknown = true)
data class PhotoResponse(
    val id: String,
    val urls: Urls,
    val user: UserResponse,
    val location: LocationResponse,
    val exif: ExifResponse?
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Urls(
        val full: String
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class UserResponse(
        val name: String,
        @JsonProperty("instagram_username")
        val instagramUsername: String?
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class ExifResponse(
        val name: String?,
        @JsonProperty("exposure_time")
        val exposureTime: String?,
        val aperture: String?,
        @JsonProperty("focal_length")
        val focalLength: String?,
        val iso: Int?
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class LocationResponse(
        val name: String?
    )

    fun toDomain(): Photo {
        return Photo(
            id = id,
            url = urls.full,
            user = User(
                name = user.name,
                instagram = user.instagramUsername?.let { "@$it" }
            ),
            location = Location(
                name = location.name
            ),
            exif = ExifInformation(
                name = exif?.name,
                exposureTime = exif?.exposureTime,
                aperture = exif?.aperture,
                focalLength = exif?.focalLength,
                iso = exif?.iso
            )
        )
    }
}
