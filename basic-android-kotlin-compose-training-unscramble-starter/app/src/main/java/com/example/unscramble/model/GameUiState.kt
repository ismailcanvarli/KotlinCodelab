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
    //Kullanıcının skoru için değişken oluşturduk.
    val score: Int = 0,
    //kullanıcının tahminde bulunduğu kelime sayısını tutuyoruz.
    val currentWordCount: Int = 1,
)
