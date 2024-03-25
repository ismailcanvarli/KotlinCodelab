package com.example.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen

/*
Rotaların (route) ismi string olur. Biz bu enum'ı yapma sebebimiz
//bu string değerleri oluşturmak.
Cupcake uygulamasının dört rotasını tanımlayarak başlayacaksınız.

Start: Üç düğmeden birinden kek miktarını seçin.
Flavor: Seçenekler listesinden lezzeti seçin.
Pickup: Seçenekler listesinden alım tarihini seçin.
Summary: Seçimleri inceleyin ve siparişi gönderin veya iptal edin.
 */
//Top app bar daki geri tuşu için eklemeler yaptık.
enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Flavor(title = R.string.choose_flavor),
    Pickup(title = R.string.choose_pickup_date),
    Summary(title = R.string.order_summary)
}

@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    //Top app bar'daki geri tuşu için uygulamada önceki sayfa varmı bakacağız
    //eğer ilk ekranda ise geri tuşumuz gözükmeyecek.
    val backStackEntry by navController.currentBackStackEntryAsState()
    //mevcut ekranın değerini güvenli bir şekilde belirlemek ve
    // onu currentScreen değişkenine atamaktır.
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )

    Scaffold(
        topBar = {
            CupcakeAppBar(
                //Mevcut ekranı belirlemek için kullanıyoruz.
                currentScreen = currentScreen,
                //Eğer önceki ekran varsa oraya dönebilir
                canNavigateBack = navController.previousBackStackEntry != null,
                //önceki sayfaya geri dönme işlemini tanımladık.
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        //Navhost oluşturduk ve istediği değerlere navController'ı verdik
        //sonrasında başlangıç rotasına başlagıç rotasının adını verdik
        //rotaların ismi basit stringler oluyor. Bu stringleri enum şeklinde tanımladık
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            //Burada navhost'un için route(yönlendirme) işlemini yapıyoruz.
            //Oluşturduğumuz enum daki start değerini alıyoruz ve buraya veriyoruz
            composable(route = CupcakeScreen.Start.name) {
                //start yönlendiricisini kullandığımızda startOrderScreen
                //composable'ını çağırıyoruz.
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
                    //Başlangıç ekranında butona basıldığında view model'daki adet miktarına
                    //Mevcut adet miktarını veriyoruz. Nav controller'ada sonraki ekranın
                    //route(rota) bilgisini veriyoruz.
                    onNextButtonClicked = {
                        viewModel.setQuantity(it)
                        navController.navigate(CupcakeScreen.Flavor.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            composable(route = CupcakeScreen.Flavor.name) {
                val context = LocalContext.current
                /*
                Kullanıcı bir lezzet seçtiğinde lezzet ekranının alt toplamı görüntülemesi
                 ve güncellemesi gerekir. Alt toplam parametresi için uiState.
                 price değerini iletin.
                 */
                SelectOptionScreen(
                    subtotal = uiState.price,
                    //Butona basıldığında bir sonraki ekranın rotasını verdik
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Pickup.name) },
                    //Back (İptel etme) butonuna basıldığında aşağıdaki fonk. çağırıyoruz.
                    // O fonksiyonda işlemleri sıfırlıyor.
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = DataSource.flavors.map { id -> context.resources.getString(id) },
                    //Başlangıç ekranında butona basıldığında view model'daki adet flavor(lezzet)
                    //değişkenini view model'a veriyoruz.
                    onSelectionChanged = { viewModel.setFlavor(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = CupcakeScreen.Pickup.name) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Summary.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = uiState.pickupOptions,
                    onSelectionChanged = { viewModel.setDate(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = CupcakeScreen.Summary.name) {
                //içinde bulunduğumuz durumu(context)'i aldık
                val context = LocalContext.current

                OrderSummaryScreen(
                    orderUiState = uiState,
                    //Bu ekrandaki butonlara basıldığında olacak işlemleri yazacağız.
                    //Burada yapılacak işlemleri sonradan ekleyeceğiz.
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String ->
                        shareOrder(context, subject = subject, summary = summary)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }

        }

    }
}

/**
Bu Composable, topBar'ı görüntüler ve geri navigasyon mümkünse geri düğmesini gösterir.
 Mevcut ekran isminde bir argüman ekledik çünkü geri tuşu hep aynı yerde olacak
 mevcut ekranı bilecek ve basıldığında önceki ekrana dönecek.
 */
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        //top app bar'ın ismi mevcut ekranın ismi olacak.
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

//Bu fonksiyonu telefondaki back tuşuna bastığımızda olacak işlem için tanımlıyoruz.
//2 adet parametre tanımlayacağız ve amaç şu olacak. Geri gelme tuşuna bastığımızda
//uygulama hangi ekrana dönsün.
private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    //En başa geldiğimiz için siparişi sıfırlıyoruz.
    viewModel.resetOrder()
    //
    navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
}

/*
Top app bar daki paylaş işlemi ile siparişimizi paylaşma işlemi için kullanacağız.
Bunu android sistemi kontrol eder. Navigation burada işlem yapmaz.

Bir intent (amaç) nesnesi oluşturun ve ACTION_SEND gibi amacı belirtin.
Bu intent (amaçla) gönderilen ek verilerin türünü belirtin. Basit bir metin parçası için
"metin/düz" kullanabilirsiniz, ancak "resim" veya "video" gibi başka türler de mevcuttur.
putExtra() yöntemini çağırarak, paylaşılacak metin veya resim gibi ek verileri amaca iletin.
 Bu intent (niyet) iki ekstrayı alacaktır: EXTRA_SUBJECT ve EXTRA_TEXT.
 intent (Amaçtan) oluşturulan bir etkinliği ileterek startActivity() bağlam yöntemini çağırın.
 */
//paylaşılacak değerleri giriyoruz
private fun shareOrder(context: Context, subject: String, summary: String) {
    //intent oluşturduk ve türünü text yani düz yazı verdik.
    //Sonra fonksiyona argüman olarak gelen değerleri ilettik.
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}