package com.example.unscramble.model

/*
Burada ise model sınıfımızı oluşturuk.
Değişen duruma sahip değerler burada bulunuyor.
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
    //Oyunun bitip bitmeme durumunu tutuyor.
    val isGameOver: Boolean = false
)
