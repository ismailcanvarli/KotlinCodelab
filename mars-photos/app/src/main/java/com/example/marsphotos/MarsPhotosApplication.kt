package com.example.marsphotos

import android.app.Application
import com.example.marsphotos.data.AppContainer
import com.example.marsphotos.data.DefaultAppContainer

// Bu sınıf, uygulama başlatıldığında oluşturulur ve uygulama seviyesindeki DI konteynerini oluşturur.
class MarsPhotosApplication: Application() {
    //
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}