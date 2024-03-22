package com.example.unscramble.data.ui.test

import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import com.example.unscramble.ui.GameViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel()

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

    /*
    Boundary case (sınır durumu)- aşırı yükleme durumu
    burada ise kelime girme yerine örnek veriyorum çok fazla kelime gireceğiz
    sonunda sonuca bakacağız
     */

    //burada uygulamanın ilk durumadaki değerlerin testini yapacağız.
    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUiState = viewModel.uiState.value
        //şifrelenmemiş değeri alıyoruz
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        // Mevcut kelimenin karıştırıldığını iddia edin.
        assertNotEquals(unScrambledWord, gameUiState.currentScrambledWord)
        // Geçerli kelime sayısının 1'e ayarlandığını iddia edin.
        assertTrue(gameUiState.currentWordCount == 1)
        // Başlangıçta puanın 0 olduğunu iddia edin
        assertTrue(gameUiState.score == 0)
        // Tahmin edilen kelimenin olmadığını söylüyoruz
        assertFalse(gameUiState.isGuessedWordWrong)
        // oyunun bitmediğini iddia ediyoruz.
        assertFalse(gameUiState.isGameOver)
    }

    //Başka bir boundary case test
    //Kullanıcı tüm kelimeleri tahmin ettiğinde oyunun durumunu test edeceğiz
    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        //kullanıcının skorunu sonsuza kadar arttırmaya çalışıyoruz
        //ancak bunun şuanki uygualamada 200'de durmasını bekleyeceğiz
        repeat(MAX_NO_OF_WORDS) {
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()
            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
            // Assert that after each correct answer, score is updated correctly.
            assertEquals(expectedScore, currentGameUiState.score)
        }
        // Assert that after all questions are answered, the current word count is up-to-date.
        assertEquals(MAX_NO_OF_WORDS, currentGameUiState.currentWordCount)
        // Assert that after 10 questions are answered, the game is over.
        assertTrue(currentGameUiState.isGameOver)

    }
}