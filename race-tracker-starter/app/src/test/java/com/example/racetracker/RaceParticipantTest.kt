/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.racetracker

import com.example.racetracker.ui.RaceParticipant
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.launch
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

    // TODO Add the tests to cover the happy path, error cases, and boundary cases.  Write unit tests for ViewModel codelab
}
