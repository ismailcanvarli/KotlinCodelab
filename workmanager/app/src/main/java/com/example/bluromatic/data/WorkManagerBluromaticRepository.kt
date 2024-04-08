package com.example.bluromatic.data

import android.content.Context
import android.net.Uri
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.workers.BlurWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * BluromaticRepository, resme bulanık uygulamak için kullanılacak olan depo tarafından
 * uygulanacak yöntemleri tanımlayan bir sınıftır.
 */

class WorkManagerBluromaticRepository(context: Context) : BluromaticRepository {

    private val workManager = WorkManager.getInstance(context)

    override val outputWorkInfo: Flow<WorkInfo?> = MutableStateFlow(null)

    /**
     * Uygulamak için WorkRequest oluşturur ve başlatır
     * @param blurLevel Resmi bulanıklaştırmak için miktar
     */
    override fun applyBlur(blurLevel: Int) {
        val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()

        // Giriş verilerini oluştur
        workManager.enqueue(blurBuilder.build())
    }

    /**
     * İşlemekte olan WorkRequests'leri iptal eder
     * */
    override fun cancelWork() {}

    /**
     * Uygulanacak bulanıklık miktarını güncellemek için bulanıklık seviyesini ve
     * işlem yapılacak Uri'yi içeren giriş veri demetini oluşturur
     * @return Resim Uri'sini String ve bulanıklık seviyesini Integer olarak içeren Data
     */
    private fun createInputDataForWorkRequest(blurLevel: Int, imageUri: Uri): Data {
        val builder = Data.Builder()
        builder.putString(KEY_IMAGE_URI, imageUri.toString()).putInt(KEY_BLUR_LEVEL, blurLevel)
        return builder.build()
    }
}
