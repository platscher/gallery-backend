package com.sant1g.app.models

data class Photo(
    val id: String,
    val url: String,
    val user: User,
    val location: Location,
    val exif: ExifInformation
)
