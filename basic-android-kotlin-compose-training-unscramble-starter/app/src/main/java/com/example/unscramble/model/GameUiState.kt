package com.example.unscramble.model

/*
Burada ise model sınıfımızı oluşturuk.
Mevcuttaki kelimenin bilgisini tutuyor.
 */

data class GameUiState(
    //Mevcut tahmin edilen değeri döndürüyoruz
    val currentScrambledWord: String = "",
    //Tahmin edilen değer yanlış değişkeni
    val isGuessedWordWrong: Boolean = false,
)
