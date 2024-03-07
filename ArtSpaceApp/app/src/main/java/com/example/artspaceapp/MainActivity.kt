package com.example.artspaceapp

import android.media.Image
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
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 10.dp,
                vertical = 50.dp
            )
    ) {
        ComposableArtworkWall(modifier)
        Spacer(modifier = Modifier.height(50.dp))
        ComposableArtworkDescriptor(year = 1515, modifier)
        Spacer(modifier = Modifier.height(25.dp))
        ComposableDisplayController(modifier)
    }
}

@Composable
fun ComposableArtworkWall(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.starry_night),
        contentDescription = null,
        modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}

@Composable
fun ComposableArtworkDescriptor(year: Int, modifier: Modifier) {
    Text(
        text = stringResource(R.string.artwork_title),
        fontSize = 28.sp
    )
    Spacer(modifier.height(15.dp))
    Text(text = stringResource(R.string.artwork_artist, year))
}

@Composable
fun ComposableDisplayController(modifier: Modifier = Modifier) {
    Row {
        Button(
            onClick = { /*TODO*/ },
        ) {
            Text(text = stringResource(R.string.previous))
        }
        Spacer(modifier.width(150.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtGalleryPreview() {
    ArtSpaceAppTheme {
        ArtGallery()
    }
}