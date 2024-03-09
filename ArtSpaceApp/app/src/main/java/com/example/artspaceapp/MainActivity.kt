package com.example.artspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ArtGalleryPreview()
                }
            }
        }
    }
}

@Composable
fun ArtGallery(modifier: Modifier = Modifier) {
    //Var tipinde başlangıç duruma sahip olan ve durumu dinamik olarak değişen
    //bir değişken tanımladım ve bu değişken ile hangi resmin koyulacağını belirledim.
    var currentStep: Int by remember { mutableStateOf(1) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 10.dp, vertical = 50.dp
            )
    ) {
        when (currentStep) {
            1 -> {
                ComposableArtworkWall(
                    drawableResourceId = R.drawable.starry_night, modifier
                )
                Spacer(modifier = Modifier.height(50.dp))
                ComposableArtworkDescriptor(
                    year = 1889,
                    nameOfTheTable = stringResource(R.string.the_starry_night),
                    painterName = stringResource(R.string.vincent_van_gogh),
                    modifier
                )
            }

            2 -> {
                ComposableArtworkWall(
                    drawableResourceId = R.drawable.mona_lisa, modifier
                )
                Spacer(modifier = Modifier.height(50.dp))
                ComposableArtworkDescriptor(
                    year = 1519,
                    nameOfTheTable = stringResource(R.string.mona_lisa),
                    painterName = stringResource(R.string.leonardo_da_vinci),
                    modifier
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        //buton değişkeni ile bu mutableState'e sahip olan değişken aynı yerde olması gerekiyor.
        //Önceki örneklerde de aynı şekil yaptım.
        Row {
            Button(
                onClick = { currentStep = 1 }) {
                Text(text = stringResource(R.string.previous))
            }
            Spacer(modifier.width(50.dp))
            Button(onClick = { currentStep = 2 }) {
                Text(text = stringResource(R.string.next))
            }
        }
    }
}

// Gösterilecek resmi burada seçtim.
@Composable
fun ComposableArtworkWall(drawableResourceId: Int, modifier: Modifier) {
    Image(
        painter = painterResource(drawableResourceId),
        contentDescription = null,
        modifier.fillMaxWidth().height(300.dp)
    )
}

//Resim ile alakalı açıklamaları burada yazdım.
//Resmin adı, yapan kişi ve yapıldığı tarih bilgileri.
@Composable
fun ComposableArtworkDescriptor(
    year: Int,
    nameOfTheTable: String,
    painterName: String,
    modifier: Modifier
) {
    Text(
        text = nameOfTheTable, fontSize = 28.sp
    )
    Spacer(modifier.height(15.dp))
    Text(text = "$painterName $year")
}

@Preview(showBackground = true)
@Composable
fun ArtGalleryPreview() {
    ArtSpaceAppTheme {
        ArtGallery()
    }
}