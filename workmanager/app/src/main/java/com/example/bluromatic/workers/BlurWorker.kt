package com.example.bluromatic.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bluromatic.DELAY_TIME_MILLIS
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
        makeStatusNotification(
            applicationContext.resources.getString(R.string.blurring_image),
            applicationContext
        )
        return withContext(Dispatchers.IO) {
            // resmi bulanıklaştırıken bir gecikme ekledik.
            delay(DELAY_TIME_MILLIS)
            // Resmi bulanıklaştırma işlemini gerçekleştir
            // @withContext işlevi, işlemi belirtilen bir Dispatcher'da çalıştırır
            return@withContext try {
                val picture = BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.android_cupcake
                )
                // Resmi bulanıklaştır
                val output = blurBitmap(picture, 1)

                // Bulanıklaştırılmış resmi dosyaya yaz
                val outputUri = writeBitmapToFile(applicationContext, output)

                makeStatusNotification(
                    "Output is $outputUri",
                    applicationContext
                )

                Result.success()
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