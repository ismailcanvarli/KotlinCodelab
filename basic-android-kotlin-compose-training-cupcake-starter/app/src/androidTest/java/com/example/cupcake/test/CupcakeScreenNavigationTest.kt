package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.CupcakeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

//Uygulama açıldığında direk test navigation controller oluşturuyoruz.
private lateinit var navController: TestNavHostController

@get:Rule
val composeTestRule = createAndroidComposeRule<ComponentActivity>()

/*
Cupcake uygulamamızda ekranlar arası navigasyonun testi için bu sınıfı oluşturduk
Bu testleri yapabilmek için build gradle (module app) dosyasına gerekli
dependencies'leri ekledik.
 */
class CupcakeScreenNavigationTest {
}

//Bir fonksiyona before eklendiğinde her @test notasyonu bulunan test edilecek
//kısımdan önce bu kısım çağırılır.
@Before
fun setupCupcakeNavHost() {
    composeTestRule.setContent {
        navController = TestNavHostController(LocalContext.current).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }
        CupcakeApp(navController = navController)
    }
}

//Başlangıç noktasının startOrderScreen olduğunu doğrulamak için test yazacağız
@Test
fun cupcakeNavHost_verifyStartDestination() {
    //buradaki iddiayı sürekli kullanacağımız için ScreenAssertions isimli bir
    //kotlin file oluşturduk. Oradan çağırdık bu assestions'ı.
    navController.assertCurrentRouteName(CupcakeScreen.Start.name)
}

//Başlangıçta top bar'da geri gelme butonu olmadığını kontrol edeceğiz.
@Test
fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
    val backText = composeTestRule.activity.getString(R.string.back_button)
    composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
}

//
@Test
fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen() {
    composeTestRule.onNodeWithStringId(R.string.one_cupcake)
        .performClick()
    navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
}

private fun navigateToFlavorScreen() {
    composeTestRule.onNodeWithStringId(R.string.one_cupcake).performClick()
    composeTestRule.onNodeWithStringId(R.string.chocolate).performClick()
}

private fun getFormattedDate(): String {
    val calendar = Calendar.getInstance()
    calendar.add(java.util.Calendar.DATE, 1)
    val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
    return formatter.format(calendar.time)
}

private fun navigateToPickupScreen() {
    navigateToFlavorScreen()
    composeTestRule.onNodeWithStringId(R.string.next)
        .performClick()
}

private fun navigateToSummaryScreen() {
    navigateToPickupScreen()
    composeTestRule.onNodeWithText(getFormattedDate())
        .performClick()
    composeTestRule.onNodeWithStringId(R.string.next)
        .performClick()
}

private fun performNavigateUp() {
    val backText = composeTestRule.activity.getString(R.string.back_button)
    composeTestRule.onNodeWithContentDescription(backText).performClick()
}