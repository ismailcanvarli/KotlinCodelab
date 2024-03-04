package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}

@Composable
fun TipTimeLayout() {
/*
başlangıç değeri olşturduk
aşağıda bir başlangıç değerli değişken duruma sahibi değişken tanımladık
Bu değeri burada tanımlamamızın sebebi buna başka composable'dan da erişmek
için kullandık.
 */
    var amountInput by remember {mutableStateOf("")}

    //klavyeden girilen değeri aldık ve double'a çevirdik
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        //Text'in altına ekledik
        EditNumberField(
            value = amountInput,
            onValueChange = {amountInput = it},
            modifier = Modifier
                .padding(bottom = 32.dp) //alt kısmına boşluk ekledik
                .fillMaxWidth() //yatayda tüm genişlik sağlayacak
        )
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))

    }
}

/**
 * Calculates the tip based on the user input and format the tip amount
 * according to the local currency.
 * Example would be "$10.00".
 */
private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

/*Bu composable'da bulunan text'te yazan bilgi değişkenine başka composable'dan
* erişmek için bu değeri bu composable'a bir değişken olarak vereceğiz
* hangi composable'a lazımsa bu değer ona değişken olarak vereceğiz
 */
@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
    ) {

    //aşağıda ise textfield'daki değişken değerden gelen value'yi ona eşitledik
    TextField(
        value = value, //bu textfield'daki bulunan değer için
        //tutar değerini textfield'daki değişen değere eşitliyoruz.
        //it ifadesinin kullanılma nedeni kendisine yani yeni değere eşitledik demek
        //buda textfield'da değer değiştiğinde çalışıyor
        onValueChange = onValueChange,
        //Text'in içine label ekledik ve giriş yapacağı şeyin iblgisini veriyoruz
        label = { Text(stringResource(R.string.bill_amount)) },
        //Tek bir satırda yazılmasını istiyoruz aşağı satıra geçmesin
        singleLine = true,
        //Klavye tipini belirteceğiz. sadece numara girsin diye böyle belirttik
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    TipTimeTheme {
        TipTimeLayout()
    }
}
