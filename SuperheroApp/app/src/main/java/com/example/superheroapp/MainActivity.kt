package com.example.superheroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.superheroapp.model.HeroesRepository
import com.example.superheroapp.ui.theme.SuperheroAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperheroAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SuperheroesApp()
                }
            }
        }
    }
}

/**
 * Composable that displays an app bar and a list of heroes.
 * Süper kahramanların listesini ekledik
 */
@Composable
fun SuperheroesApp() {
    //Scaffold(iskelet) kullandık TopAppBar için
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar()
        }
    ) {
        /* Important: It is not a good practice to access data source directly from the UI.
        In later units you will learn how to use ViewModel in such scenarios that takes the
        data source as a dependency and exposes heroes.
        Ui'dan direk verilere ulaşmak çok mantıklı ve pratic değil.
        İlerleyen aşamada view model öğreneceğiz. Orada bu işi yapacağız
         */
        val heroes = HeroesRepository.heroes
        HeroesList(heroes = heroes, contentPadding = it)
    }
}

/**
 * Composable that displays a Top Bar with an icon and text.
 *Composable'da top app bard ve ve teşt gösteriliyor
 *
 * @param modifier modifiers to set to this composable
 * Center align top app bar için bu aşağıdaki OptIn kısmını kullanmak zorundayız
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge,
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SuperHeroesPreview() {
    SuperheroAppTheme {
        SuperheroesApp()
    }
}