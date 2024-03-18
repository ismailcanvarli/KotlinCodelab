/*
View model dosyamızı burada oluşturuyoruz.
 */

package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import com.example.unscramble.model.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//mevcut kelimeyi alacak değişken oluşturduk.
private lateinit var currentWord: String
// Kullanılmış kelimelerin listesini tutuyoruz.
private var usedWords: MutableSet<String> = mutableSetOf()

class GameViewModel : ViewModel() {
    //Oyunun başlangıcında oyunu sıfırlıyoruz.
    init {
        resetGame()
    }

    // Game UI state
    //Değişen verileri gözlemlemek için oluşturduk
    // Bu değer sadece bu sınıf içinden değiştirilebilecek
    private val _uiState = MutableStateFlow(GameUiState())

    //başka sınıflar tarafından sadece görüntülenebilecek.
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    //Mevcut kelimeyi karıştırmak için kullanılan fonksiyon
    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    //Rastgele bir kelime seçmek ve karıştırmak için kullanılan fonksiyon
    private fun pickRandomWordAndShuffle(): String {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }
}