package com.example.marsphotos.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Mars API'den alınan fotoğraf bilgisini saklar.
@Serializable
data class MarsPhoto(
    @SerialName(value = "img_src")
    val imgSrc: String
)
