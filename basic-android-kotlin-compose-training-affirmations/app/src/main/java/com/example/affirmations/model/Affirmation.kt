package com.example.affirmations.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * [Affirmation] is the data class to represent the Affirmation text and imageResourceId
 */
data class Affirmation(
    //burada değerler id olarak verildi ancak annotation ile bunun string bir değer
    //olduğu veya bir drawable yani resim dosyayı olduğu belirtildi.
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)
