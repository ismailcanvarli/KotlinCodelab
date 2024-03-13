/*
Burada da yazı tiplerini fontlarını ekliyoruz.
Buraya eklediğimiz fontları öncesinde res klasörünün altına ekledik
orada font isimli bir android directory açtık.
sonrasıda google font denen web sitesinden istediğimiz fontları seçtik
isimlerini düzelttik ve buraya ekledik
 */
package com.example.woof.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.woof.R
import androidx.compose.ui.text.TextStyle

//bunlar eklediğimiz yazı tipleri
val AbrilFatface = FontFamily(
    Font(R.font.abril_fatface_regular)
)

val Montserrat = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)

// Kullandığımız yazı tiplerini değiştirdik
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = AbrilFatface,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)