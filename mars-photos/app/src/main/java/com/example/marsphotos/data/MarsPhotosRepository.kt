package com.example.marsphotos.data

import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.MarsApi

/**
 * Mars API'den fotoğrafları almak için bir arayüz.
 * Take an interface to get photos from the Mars API.
 */
interface MarsPhotosRepository {
    // Mars API'den fotoğrafları alır.
    suspend fun getMarsPhotos(): List<MarsPhoto>
}

class NetworkMarsPhotosRepository() : MarsPhotosRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> {
        return MarsApi.retrofitService.getPhotos()
    }
}