package com.example.tiptime

import android.os.Bundle
import androidx.compose.material3.Switch
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat

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
    var amountInput by remember { mutableStateOf("") }
    //Bağış yüzdesini belirlemek için eklediğimiz değişken
    var tipInput by remember { mutableStateOf("") }
    //switch için ekledik bu değeri ve başlangıç değeri false olarak atadık
    var roundUp by remember { mutableStateOf(false) }

    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    //klavyeden girilen değeri aldık ve double'a çevirdik
    val amount = amountInput.toDoubleOrNull() ?: 0.0

    /*
    yapılan bağışı hesaplayan fonksiyon.
    Amount yazdığımız değer değişkeni
    tipPercent istenilen yüzde
    roundUp ise yukarı yuvarlama için kullanılan switch için
     */
    val tip = calculateTip(amount, tipPercent, roundUp)

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
            label = R.string.bill_amount,
            /*
            Klavye tipini değişken olarak verdik. Çünkü iki farlı
            editNumberField değişkeni var. İlkinde ente tuşu ileri gitmek için
            kullanılıyor ikincisinde ise bitirmek için kullanıcaz.
             */
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = amountInput,
            onValueChange = { amountInput = it },
            modifier = Modifier
                .padding(bottom = 32.dp) //alt kısmına boşluk ekledik
                .fillMaxWidth() //yatayda tüm genişlik sağlayacak
        )
        //Yeni bir giriş girdi text'i ekledik. Yüzdeyi belirlemek için
        EditNumberField(
            label = R.string.how_was_the_service,
            //Burada klavye'de ki enter tuşu işlemi bitirmek için kullanılır.
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = tipInput,
            onValueChange = { tipInput = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        //switch ve onun yazısı için eklediğimiz kısım.
        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it },
            modifier = Modifier.padding(bottom = 32.dp)
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
private fun calculateTip(
    amount: Double,
    tipPercent: Double = 15.0,
    roundUp: Boolean
): String {
    var tip = tipPercent / 100 * amount
    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

/*Bu composable'da bulunan text'te yazan bilgi değişkenine başka composable'dan
* erişmek için bu değeri bu composable'a bir değişken olarak vereceğiz
* hangi composable'a lazımsa bu değer ona değişken olarak vereceğiz
 */
@Composable
fun EditNumberField(
    /*
    label değişkeni ekleyeceğiz. Müşteri yapacağı bağış oranını kendi belirleyecek
    @StringRes kullanmamızın sebebi du değeri kullanıcı text'e girecek
     bizde oradan alacağımız için oldu. type-safe oluyor bu şekilde.
     */
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
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
        //Text'in içine label ekledik. Kullanıcı kendi belirleyecek yapacağı bağışı
        label = { Text(stringResource(label)) },
        //Tek bir satırda yazılmasını istiyoruz aşağı satıra geçmesin
        singleLine = true,
        /*Klavye tipini belirteceğiz. sadece numara girsin diye böyle belirttik
        Enter tuşuna basıldığında sonraki text'e geçme işlemini ekliyoruz
         */
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

//switch button ekleyeceğiz. Hesabın aşağı yukarı yuvarlanması ile ilgili
@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //hesabı yuvarlayalım mı yazısı için text ekledik
        Text(text = stringResource(R.string.round_up_tip))
        //Switch'i satırın sonuna ekledik.
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    TipTimeTheme {
        TipTimeLayout()
    }
}
