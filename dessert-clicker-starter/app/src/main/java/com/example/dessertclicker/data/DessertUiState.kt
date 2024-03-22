package com.example.dessertclicker.data

import androidx.annotation.DrawableRes
import com.example.dessertclicker.data.Datasource.dessertList

/*
view model sınıfı için bu sınıfı oluşturduk.
View model uygulama için view modelda kontrol edeceğimiz değişkenleri yazıyoruz.
*/
//Bir ekranın mevcut durumunu temsil eder.
data class DessertUiState(
    // Mevcut tatlı indeksi, varsayılan olarak 0'dır.
    val currentDessertIndex: Int = 0,
    // Satılan tatlıların sayısı, varsayılan olarak 0'dır.
    val dessertsSold: Int = 0,
    // Gelir, varsayılan olarak 0'dır.
    val revenue: Int = 0,
    // Mevcut tatlı fiyatı, mevcut tatlı indeksine göre belirlenir.
    // Eğer dessertList[currentDessertIndex].price mevcut değilse, varsayılan değeri 0 olur.
    val currentDessertPrice: Int = dessertList[currentDessertIndex].price,
    // Mevcut tatlı resminin kaynağı, mevcut tatlı indeksine göre belirlenir.
    @DrawableRes val currentDessertImageId: Int = dessertList[currentDessertIndex].imageId
)