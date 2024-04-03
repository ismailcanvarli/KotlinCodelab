package com.example.marsphotos.data

import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.MarsApiService

/**
 * Mars API'den fotoğrafları almak için bir arayüz.
 * Take an interface to get photos from the Mars API.
 */
interface MarsPhotosRepository {
    // Mars API'den fotoğrafları alır.
    suspend fun getMarsPhotos(): List<MarsPhoto>
}


 // Mars API'den fotoğrafları almak için bir arayüz.
class NetworkMarsPhotosRepository(
    private val marsApiService: MarsApiService
) : MarsPhotosRepository {
    // Mars API'den Mars fotoğraflarını alır.
    override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}