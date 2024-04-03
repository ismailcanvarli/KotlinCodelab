package com.example.marsphotos.network

import com.example.marsphotos.model.MarsPhoto
import retrofit2.http.GET

// Mars API servisi için GET isteği yapar. Bu istek Mars API'den fotoğrafları alır.
interface MarsApiService {
    // GET isteği yapar ve Mars fotoğraflarını alır.
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}