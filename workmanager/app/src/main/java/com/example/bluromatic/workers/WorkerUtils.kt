package com.example.bluromatic.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.bluromatic.CHANNEL_ID
import com.example.bluromatic.NOTIFICATION_ID
import com.example.bluromatic.NOTIFICATION_TITLE
import com.example.bluromatic.OUTPUT_PATH
import com.example.bluromatic.R
import com.example.bluromatic.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.example.bluromatic.VERBOSE_NOTIFICATION_CHANNEL_NAME
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

private const val TAG = "WorkerUtils"

/**
 * Eğer mümkünse, bir heads-up bildirimi olarak gösterilen bir Bildirim oluşturun.
 *
 * Bu codelab için, bu, farklı adımların ne zaman başladığını bildiğinizden
 * emin olmak için bir bildirim göstermek için kullanılır.
 *
 * @param message Mesaj bildirimde gösterilir
 * @param context Toast oluşturmak için gerekli olan Context
 */
fun makeStatusNotification(message: String, context: Context) {

    // Eğer gerekiyorsa bir kanal oluştur
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Apı 26 ve üzeri için NotificationChannel oluştur
        // Çünkü NotificationChannel sınıfı yeni ve destek kütüphanede yok
        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // kanalı ekle
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Bildirim oluştur
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Bildirimi göster
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}

/**
 * Verilen Bitmap görüntüsünü bulanıklaştırır
 * @param bitmap Bulanıklaştırılacak görüntü
 * @param blurLevel Bulanıklık seviyesi girişi
 * @return Bulanıklaştırılmış bitmap görüntüsü
 */
@WorkerThread
fun blurBitmap(bitmap: Bitmap, blurLevel: Int): Bitmap {
    val input = Bitmap.createScaledBitmap(
        bitmap,
        bitmap.width/(blurLevel*5),
        bitmap.height/(blurLevel*5),
        true
    )
    return Bitmap.createScaledBitmap(input, bitmap.width, bitmap.height, true)
}

/**
 * Bitmap'i geçici bir dosyaya yazar ve dosya için Uri'yi döndürür
 * @param applicationContext Uygulama bağlamı
 * @param bitmap Geçici dosyaya yazılacak Bitmap
 * @return Bitmap ile geçici dosya için Uri
 * @throws FileNotFoundException Bitmap dosyası bulunamazsa fırlatır
 */
@Throws(FileNotFoundException::class)
fun writeBitmapToFile(applicationContext: Context, bitmap: Bitmap): Uri {
    val name = String.format("blur-filter-output-%s.png", UUID.randomUUID().toString())
    val outputDir = File(applicationContext.filesDir, OUTPUT_PATH)
    if (!outputDir.exists()) {
        outputDir.mkdirs() // should succeed
    }
    val outputFile = File(outputDir, name)
    var out: FileOutputStream? = null
    try {
        out = FileOutputStream(outputFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
    } finally {
        out?.let {
            try {
                it.close()
            } catch (e: IOException) {
                Log.e(TAG, e.message.toString())
            }
        }
    }
    return Uri.fromFile(outputFile)
}
