package com.example.bluromatic.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

// Worker sınıfı, işlemi başlatmak ve sonlandırmak için gerekli
// olan Context ve WorkerParameters parametrelerini alır
private const val TAG = "BlurWorker"

// Resmi bulanıklaştırmak için arka planda çalışan işlemi gerçekleştiren Worker sınıfı
// Worker sınıfı, işlemi başlatmak ve sonlandırmak için gerekli olan
// Context ve WorkerParameters parametrelerini alır
// CoroutineWorker sınıfını genişletir ve Worker sınıfını uygular
class BlurWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {

        val resourceUri = inputData.getString(KEY_IMAGE_URI)
        val blurLevel = inputData.getInt(KEY_BLUR_LEVEL, 1)

        return withContext(Dispatchers.IO) {
            // resmi bulanıklaştırıken bir gecikme ekledik.
            delay(DELAY_TIME_MILLIS)
            // Resmi bulanıklaştırma işlemini gerçekleştir
            // @withContext işlevi, işlemi belirtilen bir Dispatcher'da çalıştırır
            return@withContext try {

                // İçerik çözücüyü al
                val resolver = applicationContext.contentResolver
                val picture = BitmapFactory.decodeStream(
                    resolver.openInputStream(Uri.parse(resourceUri))
                )
                // Resmi bulanıklaştır
                val output = blurBitmap(picture, blurLevel)

                // Bulanıklaştırılmış resmi dosyaya yaz
                val outputUri = writeBitmapToFile(applicationContext, output)

                makeStatusNotification(
                    "Output is $outputUri", applicationContext
                )

                // Giriş verilerini kontrol et ve hata durumunda bir hata döndür
                require(!resourceUri.isNullOrBlank()) {
                    val errorMessage =
                        applicationContext.resources.getString(R.string.invalid_input_uri)
                    Log.e(TAG, errorMessage)
                    errorMessage
                }
                // Çıktı verilerini ayarla ve işlemi başarılı olarak işaretle
                val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())

                // Çıktı verilerini döndür
                Result.success(outputData)
            } catch (throwable: Throwable) {
                // Hata durumunda log kaydı oluştur.
                Log.e(
                    TAG,
                    applicationContext.resources.getString(R.string.error_applying_blur),
                    throwable
                )
                Result.failure()
            }

        }


    }
}