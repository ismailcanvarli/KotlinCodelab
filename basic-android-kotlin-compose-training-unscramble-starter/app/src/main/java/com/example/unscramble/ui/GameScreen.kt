package com.example.unscramble.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble.R
import com.example.unscramble.ui.theme.UnscrambleTheme

/*
Burada oyunun genel yapısı bulunmaktadır.
Diğer composable'lar buranın içine konumlanmıştır.
Butonlar ve başlıkta buradadır.
GameScreen composable'a view model değerini verdik
 */
@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel()
) {/*
    GameViewModel sınıfının uiState akışını collectAsState() fonksiyonuyla
    gözlemleyerek gameUiState adında bir yerel değişkende saklıyor.
    collectAsState() fonksiyonu, bir akışı izleyen Compose bileşenlerine,
    akıştaki en son değeri sağlar. Bu sayede, gameUiState değişkeni, GameViewModel
    sınıfında _uiState içeriğindeki değişikliklerin otomatik olarak takip edilmesini sağlar.
    Yani, GameViewModel içindeki _uiState değiştiğinde, gameUiState otomatik olarak güncellenir
    ve bu güncellenmiş değer, UI'nın doğru şekilde yeniden çizilmesini sağlar.
     */
    val gameUiState by gameViewModel.uiState.collectAsState()
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.app_name),
            style = typography.titleLarge,
        )
        //Gerekli değişimi aktarabilmek için gönderdiğimiz değeri aldığımız değere
        //Kullanıcının yaptığı tahmin değerini alıyoruz
        //kullanıdını tahminini değiştirme için view modelden gerekli fonk çağırıyoruz
        GameLayout(
            onUserGuessChanged = {gameViewModel.updateUserGuess(it) },
            userGuess = gameViewModel.userGuess,
            //Butona basuldığında yada burada klavyede entere basıldığında
            //kullanıcının tahminini kontrol ediyoruz
            onKeyboardDone = {gameViewModel.checkUserGuess()},
            currentScrambledWord = gameUiState.currentScrambledWord,
            //Kullanıcının tahmini doğrumu yanlış mı buradan bakıyoruz.
            isGuessWrong = gameUiState.isGuessedWordWrong,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(mediumPadding)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Butona basıldığında kullanıcının tahminini kontrol ediyoruz.
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {gameViewModel.checkUserGuess()}) {
                Text(
                    text = stringResource(R.string.submit), fontSize = 16.sp
                )
            }

            OutlinedButton(
                onClick = { }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.skip), fontSize = 16.sp
                )
            }
        }

        GameStatus(score = 0, modifier = Modifier.padding(20.dp))
    }
}

/*
Kişinin puanını gösteren composable'dır. Card içinde tasarlanmıştırç
Başlangıç değeri 0'dır.
 */
@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.score, score),
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

/*
Rastgele kelimenin gösterildiği ve bizim tahminimizi girdiğimiz yer.
burada ki composable'a viewModel'dan gelen değeri veriyoruz.
Kullanıcının tahminini veriyoruz
Rastgele ürettiğimiz kelimeyi veriyoruz
Kullanıcının tahminin ettiği kelimeyi veriyoruz.
 */
@Composable
fun GameLayout(
    onUserGuessChanged: (String) -> Unit,
    userGuess: String,
    onKeyboardDone: () -> Unit,
    currentScrambledWord: String,
    isGuessWrong: Boolean,
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                text = stringResource(R.string.word_count, 0),
                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )
            Text(
                text = currentScrambledWord, style = typography.displayMedium
            )
            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = typography.titleMedium
            )
            //Bu çevresinde çizgi olan text field oluyor. Diğerki filled
            OutlinedTextField(
                value = userGuess,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = onUserGuessChanged,
                label = {
                    if (isGuessWrong) {
                        Text(stringResource(R.string.wrong_guess))
                    } else {
                        Text(stringResource(R.string.enter_your_word))
                    } },
                //hata verme işleminini kullanıcının sonucuna göre belirledik
                isError = isGuessWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { }))
        }
    }
}

/*
 * burada kullanıcının sonuç skorunu gösteren bir alet dialog vardır.
 * AlertDialog içinde ise tekrar oynama ve oyundan çıkma seçenekleri vardır.
 */
@Composable
private fun FinalScoreDialog(
    score: Int, onPlayAgain: () -> Unit, modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(onDismissRequest = {
        // Dismiss the dialog when the user clicks outside the dialog or on the back
        // button. If you want to disable that functionality, simply use an empty
        // onCloseRequest.
    },
        title = { Text(text = stringResource(R.string.congratulations)) },
        text = { Text(text = stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = {
                activity.finish()
            }) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.play_again))
            }
        })
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    UnscrambleTheme {
        GameScreen()
    }
}
