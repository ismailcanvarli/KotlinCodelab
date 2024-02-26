package com.example.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happybirthday.ui.theme.HappyBirthdayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyBirthdayTheme {
                // Burada yüzey'i tüm ekranı kaplamasını söyledik
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingImage(
                        //Mesaj kısmında ismin üstüne gelip extract resource dedik
                        //onun adını happy_birthday_text olarak değiştirdik
                        //değer olarakta bu değeri res-values-strings.xml içine koyduk
                        message = getString(R.string.happy_birthday_text),
                        from = "From İsmail Can Varlı",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

//burada mesaj değişkeni, kimden geldiğinin bilgisini ve modifier değişkenini aldık
@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text( //Doğum günün kutlu olsun yazısının özelliklerini yazıyoruz
            text = message,
            fontSize = 90.sp,
            lineHeight = 116.sp,
            textAlign = TextAlign.Center
        )
        Text( //Kimden geldiğinin text'in özelliklerini yazıyoruz.
            text = from,
            fontSize = 28.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
    }

}

//Burada ise mesaj kısmında ne yazılacağını yazdık.
//fonksiyona parametreleri verdik.
@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
    HappyBirthdayTheme {
        GreetingImage(
            message = stringResource(R.string.happy_birthday_text),
            from = stringResource(R.string.signature_text)
        )
    }
}

//Arka plan görseli için hazılıyoruz.
//yine onuda composable anotation'ı ile gösteriyoruz.
@Composable
fun GreetingImage(message: String, from: String, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.androidparty)
    Box(modifier) {//Boş cisimleri üst üste getirmemize yarar
        Image(
            painter = image, //Resmi indirdiğimiz resim olacak seçtik.
            contentDescription = null, //Resmin açıklaması demek
            contentScale = ContentScale.Crop, //Resmi ölçeklendirmeye yarar
            alpha = 0.6F //Resmin görünürlüğünü azalttık
        )
        GreetingText( //greetingText fonksiyonunu bunun üstünde çağırdık
            message = message,
            from = from,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp))
    }
}