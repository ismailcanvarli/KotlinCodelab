package com.example.tiptime

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.tiptime.ui.theme.TipTimeTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

/*
Bu sınıfları kendimiz oluştrduk. Dosya yapısını felanda kendimiz oluşturduk.
Src dosyasının altına eklediğimiz androidTest isimli dosya bunun içindi.

Burada ise ui kısmında test işlemini gerçekleştirdik. Layout'u felan ekledik.
 */
class TipUITests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip() {
        composeTestRule.setContent {
            TipTimeTheme {
                TipTimeLayout()
            }
        }
        //OnNodeWithText fonskiyonu ile o inputun için sanki 10 değerini koyduk gibi yaptık
        composeTestRule.onNodeWithText("Bill Amount")
            .performTextInput("10")
        composeTestRule.onNodeWithText("Tip Percentage")
            .performTextInput("20")
        //Burada beklenen değer değişkenini ekledik.
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        //Burada ise beklenen değerin sonuçta çıkan değere eşit olup olmadığına baktık.
        composeTestRule.onNodeWithText("Tip Amount: $expectedTip").assertExists(
            "No node with this text was found."
        )
    }
}