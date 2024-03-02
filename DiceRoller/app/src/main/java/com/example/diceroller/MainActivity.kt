package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.diceroller.ui.theme.DiceRollerTheme
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceRollerApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}


@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    //result değerini şöyle tanımladık.
    //Bir başlangıç değeri var onun değerini 1'e eşitledik.
    var result by remember { mutableIntStateOf(1) }
    //burada ise result'tan gelen değere göre farklı görsel değerini eşitliyoruz.
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    Column( //Buton ile görseli alt alta sıralamak için kullandık.
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally //Ortaladık
    ) {
        Image( //Zar resmi koyacağımız için bir image oluşturduk
            painter = painterResource(imageResource),
            contentDescription = result.toString() //İçerik açıklamasını eklemek zorundayız
        )
        //Spacer iki cisim arasına mesafe (boşluk) koymak için kullanılır
        Spacer(modifier = Modifier.height(16.dp))
        //Buton ekledik butona basıldığında sonuç değerini 1-6 arasında
        //rastgele seçecek şekilde ayarladık.
        Button(onClick = { result = (1..6).random() }) {
            Text(stringResource(R.string.roll)) //buton text'ini string.xml sınıfına koyduk
        }
    }
}

