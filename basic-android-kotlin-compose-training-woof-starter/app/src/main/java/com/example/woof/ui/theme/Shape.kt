/*
Shape sınıfında kullandığımız şekillerin bilgisi bulunmaktadır.
 */
package com.example.woof.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

//Burada şekilleri ve büyüklüklerini ekliyoruz.
val Shapes = Shapes(
    small = RoundedCornerShape(50.dp),
    medium = RoundedCornerShape(bottomStart = 16.dp, topEnd = 16.dp)
)