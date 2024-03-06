package com.example.tiptime

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat

/*
Bu sınıfları kendimiz oluştrduk. Dosya yapısını felanda kendimiz oluşturduk.
Src dosyasının altına eklediğimiz test isimli dosya bunun içinde.

Belirli bir değer için bu fonksiyonun testini hazırladık.
fonksiyonun istediği değerleri hazırladık ve fonksiyona verdik
sonrasında ise bizim verdiğimiz sonuç ile çıkan sonuca bakıp
eşit mi diye kontrol ettik.
 */
class TipCalculatorTests {

    @Test
    fun calculateTip_20PercentNoRoundup() {
        val amount = 10.00
        val tipPercent = 20.00
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = calculateTip(
            amount = amount,
            tipPercent = tipPercent,
            false
        )
        assertEquals(expectedTip, actualTip)
    }
}