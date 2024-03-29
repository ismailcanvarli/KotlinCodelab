package com.example.racetracker.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException

/**
 * Bu sınıf, yarış katılımcısı için state holder temsil eder.
 */
class RaceParticipant(
    val name: String,
    val maxProgress: Int = 100,
    val progressDelayMillis: Long = 500L,
    private val progressIncrement: Int = 1,
    private val initialProgress: Int = 0
) {
    init {
        require(maxProgress > 0) { "maxProgress=$maxProgress; must be > 0" }
        require(progressIncrement > 0) { "progressIncrement=$progressIncrement; must be > 0" }
    }

    /**
     * Katılımcının mevcut ilerlemesini temsil eder.
     */
    var currentProgress by mutableStateOf(initialProgress)
        private set

    //Koşma işlevini simüle edecek. Totalde 100 kere tekrar edecek.
    //Kullanıcının hızına göre farklı ilerleme kat edmiş olacak kullanıcılar.
    suspend fun run() {
        try {
            while (currentProgress < maxProgress) {
                //Suspension point(askıya alma noktası): ekranın sol tarafında bunun simge fonksiyonun
                // askıya alınabileceği ve daha sonra tekrar devam edebileceği askıya alma noktasını gösterir.
                delay(progressDelayMillis)
                currentProgress += progressIncrement
            }
        } catch (e: CancellationException) {
            Log.e("RaceParticipant", "$name: ${e.message}")
            throw e // Always re-throw CancellationException.
        }
    }

    /**
     * [initialProgress] değerinden bağımsız olarak sıfırlama işlevi,
     * [currentProgress] değerini 0'a sıfırlayacaktır.
     */
    fun reset() {
        currentProgress = 0
    }
}

/**
 * Doğrusal ilerleme göstergesi 0-1 aralığında ilerleme değeri bekler.
 * Bu özellik gösterge gereksinimlerini karşılamak için ilerleme faktörünü hesaplar.
 */
val RaceParticipant.progressFactor: Float
    get() = currentProgress / maxProgress.toFloat()
