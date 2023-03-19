package com.sant1g.app.clients.unsplash.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CollectionPhoto(
    val id: String
)
