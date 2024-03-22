package com.example.cupcake.data

/**
 * [quantity], [flavor], [dateOptions], seçilen alım [date] ve [price] açısından
 * mevcut UI durumunu temsil eden veri sınıfı.
 */
data class OrderUiState(
    /** Seçilen cupcake miktarı (1, 6, 12) */
    val quantity: Int = 0,
    /** Siparişteki cupcake'lerin lezzeti (örneğin "Çikolata", "Vanilya", vs..) */
    val flavor: String = "",
    /** Alım için seçilen tarih (örneğin "1 Ocak") */
    val date: String = "",
    /** Siparişin toplam fiyatı */
    val price: String = "",
    /** Sipariş için mevcut alım tarihleri */
    val pickupOptions: List<String> = listOf()
)
