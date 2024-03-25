package com.example.cupcake.data

import com.example.cupcake.R

//oluşturduğumuz veri sınıfına göre ekleme işlemlerini gerçekleştirdik.
object DataSource {
    //Uygulama da kullanacağımız flavors(lezzetler) ekledik.
    val flavors = listOf(
        R.string.vanilla,
        R.string.chocolate,
        R.string.red_velvet,
        R.string.salted_caramel,
        R.string.coffee
    )

    //Kaç adet cupcake seçileceğini girdik.
    // İlk ekran bu değerden birini seçiyoruz.
    val quantityOptions = listOf(
        Pair(R.string.one_cupcake, 1),
        Pair(R.string.six_cupcakes, 6),
        Pair(R.string.twelve_cupcakes, 12)
    )
}
