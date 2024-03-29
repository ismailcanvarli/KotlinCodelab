package com.example.racetracker

import com.example.racetracker.ui.RaceParticipant
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RaceParticipantTest {
    private val raceParticipant = RaceParticipant(
        name = "Test",
        maxProgress = 100,
        progressDelayMillis = 500L,
        initialProgress = 0,
        progressIncrement = 1
    )

    //Yarış başladıktan sonra ilerlemenenin doğru şekilde olup olmadığını kontrol edeceğiz.
    @Test
    fun raceParticipant_RaceStarted_ProgressUpdated() = runTest {
        val expectedProgress = 1
        //Yarışın başlangıcını simüle edeceğiz.
        launch { raceParticipant.run() }
        //Test yürütme süresinin kısaltılmasına yardımcı olur. Testi hızlandırıyor.
        advanceTimeBy(raceParticipant.progressDelayMillis)
        runCurrent()
        //ilerlemeden emin olmak için beklenen değerle yarıştaki değerin aynı olduğuna bakıyoruz.
        assertEquals(expectedProgress, raceParticipant.currentProgress)
    }

    //Yarışın bitişini kontrol edeceğiz.
    @Test
    fun raceParticipant_RaceFinished_ProgressUpdated() = runTest {
        launch { raceParticipant.run() }
        advanceTimeBy(raceParticipant.maxProgress * raceParticipant.progressDelayMillis)
        runCurrent()
        //sonuç 100 mü ona baktık.
        assertEquals(100, raceParticipant.currentProgress)
    }

    //Yarışın sıfırlanmasını kontrol edeceğiz.
    @Test
    fun raceParticipant_BoundaryParameters_RaceCompleted() = runBlocking {
        val raceParticipant = RaceParticipant(
            name = "Test",
            maxProgress = 1, // Sınır parametresi
            progressDelayMillis = 500L,
            initialProgress = 0,
            progressIncrement = 1
        )
        // Yarışı başlatın
        val job = launch { raceParticipant.run() }

        // Yarışın bitmesini bekleyin
        delay(raceParticipant.progressDelayMillis * raceParticipant.maxProgress)
        job.join()

        // Yarışın tamamlandığını doğrulayın
        assertEquals(raceParticipant.maxProgress, raceParticipant.currentProgress)
    }

    //İlerleme artışı sıfır olamaz
    @Test(expected = IllegalArgumentException::class)
    fun raceParticipant_ProgressIncrementZero_ExceptionThrown() = runTest {
        // İlerleme artışı sıfır olamaz
        RaceParticipant(name = "Progress Test", progressIncrement = 0)
    }

    //Maksimum ilerleme sıfır olamaz
    @Test(expected = IllegalArgumentException::class)
    fun raceParticipant_MaxProgressZero_ExceptionThrown() {
        // Maksimum ilerleme sıfır olamaz
        RaceParticipant(name = "Progress Test", maxProgress = 0)
    }
}
