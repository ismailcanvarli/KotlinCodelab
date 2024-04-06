package com.example.bluromatic

//Uygulama içinde kullandığımız sabitler
// Bildirim Kanalı sabitleri

// Arka planda çalışan işlemlerin ayrıntılı bildirimleri için Bildirim Kanalı adı
val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications whenever work starts"
val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1

// Resim manipülasyon işleminin adı
const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

// Diğer anahtarlar
const val OUTPUT_PATH = "blur_filter_outputs"
const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
const val TAG_OUTPUT = "OUTPUT"
const val KEY_BLUR_LEVEL = "KEY_BLUR_LEVEL"

// Bekleme süresi sabiti
const val DELAY_TIME_MILLIS: Long = 3000
