package com.example.unscramble.data.ui.test

import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import com.example.unscramble.ui.GameViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel()

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }

    /*
    Test fonksiyonu nasıl isimlendirilmelidir.
        Note: The code above uses the thingUnderTest_TriggerOfTest_ResultOfTest
        format to name the test function name:
        thingUnderTest = gameViewModel
        TriggerOfTest = CorrectWordGuessed
        ResultOfTest = ScoreUpdatedAndErrorFlagUnset
     */

    //Succes path için bu fonksiyonu oluşturacağız.
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

    /*
    Error path (yanlış değerler) için bu testi oluşturacağız.
    Yanlış kelime vereceğiz ve sonucun ne olacağına bakacağız.
     */
    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        //doğru olmayan kelimemiz
        val incorrectPlayerWord = "and"

        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        val currentGameUiState = viewModel.uiState.value
        // Oyuncunun puanının değişmediğini iddia ediyoruz
        assertEquals(0, currentGameUiState.score)
        // doğru kelimenin kullanıcının girdiği kelime olduğunu iddia ediyoruz
        assertTrue(currentGameUiState.isGuessedWordWrong)

    }
}