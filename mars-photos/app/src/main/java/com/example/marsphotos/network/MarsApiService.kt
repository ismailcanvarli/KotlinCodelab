package com.example.marsphotos.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

// Mars API'nin base URL'si
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

// Retrofit servisini oluştur. Bu servis Mars API'ye bağlanır.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

// Mars API servisi için GET isteği yapar. Bu istek Mars API'den fotoğrafları alır.
interface MarsApiService {
    // Mars API'den fotoğrafları alır. Bu istek GET isteğidir.
    @GET("photos")
    suspend fun getPhotos(): String

}

// Mars API servisini oluştur. Bu servis Mars API'ye bağlanır.
object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}