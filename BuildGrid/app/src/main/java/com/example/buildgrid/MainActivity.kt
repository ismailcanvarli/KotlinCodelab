package com.example.buildgrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.buildgrid.data.DataSource
import com.example.buildgrid.model.Topic
import com.example.buildgrid.ui.theme.BuildGridTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuildGridTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    /*
                    Burada şeklimizi sağdan, soldan ve yukarıdan padding(boşluk bırakma) yaptık.
                    Bu padding işlemi içinde tek tek 8.dp gibi değer gireceğimize bu değeri
                    res'in altında values'in altında dimen isimli dosyanın içine yazdık.
                    Çünkü bu değeri projeimizde çok fazla kere kullanıyoruz.
                     */
                    TopicGrid(
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_small),
                            top = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.padding_small),
                        )
                    )
                }
            }
        }
    }
}

//TODO bu layzVerticalGrid bizim projede kullanılır.
/*
   LayzVerticalGrid kullandık çünkü dikeyde ızagara görünü sağlamak istiyorduk
   Sadece tek bir resim istesek layzColumn kullanabilirdik.
   1 satırda kaç tane nesne koyacağımızın bilgisini verdik.
   yatayda ve dikeyde ne kadar boşluk vereceğimizin bilgisini yazdık.
   İtemlarıda datasource sınıfındaki topics fınksiyonundan aldık.
    */
@Composable
fun TopicGrid(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = modifier
    ) {
        items(DataSource.topics) { topic ->
            TopicCard(topic)
        }
    }
}

//TODO Card tasarımı bunuda projede kullanırım.
/*
Burada zaten hali hazırda column işini hallettik.
Column'un içinde card tasarımı yapacağız uygulamaları göstermek için
Card'ın içinde row'un içinde box'ın içine image koyuyoruz.
image'in yanınada yazı için box'ın dışında column daha ekliyoruz.
 */
@Composable
fun TopicCard(topic: Topic, modifier: Modifier = Modifier) {
    Card {
        Row {
            Box {
                //burada ki aspectRatio görselin en boy oranını temsil ediyor.
                //görseli resmini topic sınıfından çağırdığımız topic nesnesinden alıyoruz
                Image(
                    painter = painterResource(id = topic.imageRes),
                    contentDescription = null,
                    modifier = modifier
                        .size(width = 68.dp, height = 68.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }

            Column {
                Text(
                    text = stringResource(id = topic.name),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_small)
                    )
                )
                //burada satırda bir icon ve yanında yazı yazılmış
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_grain),
                        contentDescription = null,
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
                    )
                    Text(
                        text = topic.availableCourses.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopicPreview() {
    BuildGridTheme {
        val topic = Topic(R.string.photography, 321, R.drawable.photography)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopicCard(topic = topic)
        }
    }
}