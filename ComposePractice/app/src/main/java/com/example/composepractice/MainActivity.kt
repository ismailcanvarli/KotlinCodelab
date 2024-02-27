package com.example.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composepractice.ui.theme.ComposePracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Gönderilecek yazıları gönderdim.
                    ShowImage(
                        firstText = stringResource(R.string.jetpack_compose_tutorial),
                        secondText = stringResource(R.string.jetpack_compose_modern),
                        thirdText = stringResource(R.string.in_this_tutorial),
                        modifier = Modifier
                            .fillMaxSize() //Tüm sayfayı kaplaması için maxsize yaptım
                    )
                }
            }
        }
    }
}

//Gerekli strignleri ve modifier'ı aldım
@Composable
fun ShowImage(
    firstText: String, secondText: String, thirdText: String, modifier: Modifier = Modifier
) { //Resmi drawable'dan aldım
    val image = painterResource(R.drawable.bg_compose_background)
    Column(
        modifier = modifier
    ) {
        Image( // resmi ekledim
            painter = image,
            contentDescription = null
        )
        Text( //text'te boyut, padding ve ortalama ekledim
            text = firstText,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = secondText,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        )
        Text(
            text = thirdText,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

//Preview kısmı sadece tasarımı gerçek zamanlı görmek için var.
@Preview(showBackground = true)
@Composable
fun ShowImagePreview() {
    ComposePracticeTheme {
        ShowImage(
            firstText = stringResource(R.string.jetpack_compose_tutorial),
            secondText = stringResource(R.string.jetpack_compose_modern),
            thirdText = stringResource(R.string.in_this_tutorial)
        )
    }
}