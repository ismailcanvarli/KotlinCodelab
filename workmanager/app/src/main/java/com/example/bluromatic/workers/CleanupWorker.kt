package com.example.bluromatic.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.OUTPUT_PATH
import com.example.bluromatic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File

//  Logcat'te bu sınıfın işlemlerini izlemek için kullanılan etiket
private const val TAG = "CleanupWorker"

/**
 * Bu sınıf işlem sırasında oluşturulan geçici dosyaları temizler
 */
class CleanupWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        /** Arka planda çalışan işlemi gerçekleştiren Worker sınıfı
         * Worker sınıfı, işlemi başlatmak ve sonlandırmak için gerekli olan
         * Context ve WorkerParameters parametrelerini alır
         */
        makeStatusNotification(
            applicationContext.resources.getString(R.string.cleaning_up_files),
            applicationContext
        )

        return withContext(Dispatchers.IO) {
            delay(DELAY_TIME_MILLIS)

            return@withContext try {
                val outputDirectory = File(applicationContext.filesDir, OUTPUT_PATH)
                if (outputDirectory.exists()) {
                    val entries = outputDirectory.listFiles()
                    if (entries != null) {
                        for (entry in entries) {
                            val name = entry.name
                            if (name.isNotEmpty() && name.endsWith(".png")) {
                                val deleted = entry.delete()
                                Log.i(TAG, "Deleted $name - $deleted")
                            }
                        }
                    }
                }
                Result.success()
            } catch (exception: Exception) {
                Log.e(
                    TAG,
                    applicationContext.resources.getString(R.string.error_cleaning_file),
                    exception
                )
                Result.failure()
            }
        }
    }
}