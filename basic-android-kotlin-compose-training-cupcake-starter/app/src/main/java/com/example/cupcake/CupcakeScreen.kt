package com.example.cupcake

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
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen

/**
Bu Composable, topBar'ı görüntüler ve geri navigasyon mümkünse geri düğmesini gösterir.
 */
@Composable
fun CupcakeAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
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

/*
Rotaların (route) ismi string olur. Biz bu enum'ı yapma sebebimiz
//bu string değerleri oluşturmak.
Cupcake uygulamasının dört rotasını tanımlayarak başlayacaksınız.

Start: Üç düğmeden birinden kek miktarını seçin.
Flavor: Seçenekler listesinden lezzeti seçin.
Pickup: Seçenekler listesinden alım tarihini seçin.
Summary: Seçimleri inceleyin ve siparişi gönderin veya iptal edin.
 */
enum class CupcakeScreen() {
    Start,
    Flavor,
    Pickup,
    Summary
}

@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

    Scaffold(
        topBar = {
            CupcakeAppBar(
                canNavigateBack = false,
                navigateUp = { /* TODO: implement back navigation */ }
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
                    //Başlangıç ekranında butona basıldığında view model'daki adet miktarnına
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
                OrderSummaryScreen(
                    orderUiState = uiState,
                    //Bu ekrandaki butonlara basıldığında olacak işlemleri yazacağız.
                    //Burada yapılacak işlemleri sonradan ekleyeceğiz.
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String ->

                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }

        }

    }
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
