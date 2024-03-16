package com.example.superheroapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.superheroapp.R

/*
    Burada kullanacağımız kahramanların fotoğraflarını
    isimlerini ve açıklamalarını eklediğimiz data class oluşturduk
 */
data class Hero(
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int
)