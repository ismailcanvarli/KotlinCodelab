package com.example.unscramble.data.ui.test

import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import com.example.unscramble.ui.GameViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel()

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }

    //Succes path için bu fonksiyonu oluşturacağız.
    /*
    Note: The code above uses the thingUnderTest_TriggerOfTest_ResultOfTest
    format to name the test function name:
    thingUnderTest = gameViewModel
    TriggerOfTest = CorrectWordGuessed
    ResultOfTest = ScoreUpdatedAndErrorFlagUnset
     */
    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        //şifrelenmemiş kelimeyi alıyoruz.
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        //viewmodel'dan kullanıcının tahmin ettiği kelimeyle şuanki kelimesini verdik
        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value

        //checkUserGuess() yöntem güncellemelerinin isGuessedWordWrong'un
        // doğru şekilde güncellendiğini iddia edin.
        assertFalse(currentGameUiState.isGuessedWordWrong)
        // Puanın doğru şekilde güncellendiğini iddia edin.
        assertEquals(20, currentGameUiState.score)
    }


}