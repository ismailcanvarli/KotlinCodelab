package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val marsPhotosRepository: MarsPhotosRepository
}

/**
 * // İmplementasyon, uygulama seviyesindeki Bağımlılık Enjeksiyonu konteyneri için.
 * Implementation for the Dependency Injection container at the application level.
 *
 * // Değişkenler tembel bir şekilde başlatılır ve aynı örnek uygulama genelinde paylaşılır.
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     // Mars API'yi çağırmak için Retrofit servis nesnesi.
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     * API çağrıları oluşturmak için Retrofit servis nesnesi.
     * Retrofit service object for creating api calls
     */
    private val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }

    /**
     * Mars fotoğrafları deposu için DI(Dependency injection) uygulaması
     * DI implementation for Mars photos repository
     */
    override val marsPhotosRepository: MarsPhotosRepository by lazy {
        NetworkMarsPhotosRepository(retrofitService)
    }
}