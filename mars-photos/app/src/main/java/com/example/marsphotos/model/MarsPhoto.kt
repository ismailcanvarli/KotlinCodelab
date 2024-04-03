package com.example.marsphotos.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Mars fotoğraf modeli. Bu model, Mars API'den alınan fotoğrafların id'sini ve img_src'sini saklar.
@Serializable
data class MarsPhoto(
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String
)
