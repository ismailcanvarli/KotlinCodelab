package com.example.buildgrid.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

//bu dosya o kart gibi gösterimleri bir araya getirilmek için oluşturuldu.
//adı üstünde data class. İsmi, kursun durumunu ve resmi birleştiriyor.
data class Topic(
    @StringRes val name: Int,
    val availableCourses: Int,
    @DrawableRes val imageRes: Int
)
