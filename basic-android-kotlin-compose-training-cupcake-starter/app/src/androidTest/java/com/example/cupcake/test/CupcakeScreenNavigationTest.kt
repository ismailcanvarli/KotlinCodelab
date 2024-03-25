package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.CupcakeScreen
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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