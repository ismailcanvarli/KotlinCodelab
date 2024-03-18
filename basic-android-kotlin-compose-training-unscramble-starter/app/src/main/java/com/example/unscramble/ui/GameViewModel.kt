package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.unscramble.model.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/*
View model dosyamızı burada oluşturuyoruz.
 */
class GameViewModel: ViewModel() {
    // Game UI state
    //Değişen verileri gözlemlemek için oluşturduk
    // Bu değer sadece bu sınıf içinden değiştirilebilecek
    private val _uiState = MutableStateFlow(GameUiState())
    //başka sınıflar tarafından sadece görüntülenebilecek.
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
}