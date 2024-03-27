package com.example.lunchtray

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.datasource.DataSource
import com.example.lunchtray.ui.EntreeMenuScreen
import com.example.lunchtray.ui.OrderViewModel
import com.example.lunchtray.ui.StartOrderScreen

// TODO: Screen enum
//Buradaki güncelleme işlemi yapıldı. top bar da gözükecek.
enum class LunchTrayScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    EntreeMenu(title = R.string.choose_entree),
    SideDishMenu(title = R.string.choose_side_dish),
    AccompanimentMenu(title = R.string.choose_accompaniment),
    Checkout(title = R.string.order_checkout)
}

// TODO: AppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayApp() {
    //Nav kontroller oluşturduk.
    val navController = rememberNavController()
    //Top app bar'daki geri tuşu için uygulamada önceki sayfa varmı bakacağız
    //eğer ilk ekranda ise geri tuşumuz gözükmeyecek.
    val backStackEntry by navController.currentBackStackEntryAsState()
    //mevcut ekranın değerini güvenli bir şekilde belirlemek ve
    // onu currentScreen değişkenine atamaktır.
    val currentScreen = LunchTrayScreen.valueOf(
        backStackEntry?.destination?.route ?: LunchTrayScreen.Start.name
    )

    // ViewModel oluşturuldu
    val viewModel: OrderViewModel = viewModel()

    Scaffold(
        topBar = {
            LunchTrayAppBar(
                currentScreenTitle = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
    }) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        //Navhost oluşturduk ve istediği değerlere navController'ı verdik
        //sonrasında başlangıç rotasına başlagıç rotasının adını verdik
        //rotaların ismi basit stringler oluyor. Bu stringleri enum şeklinde tanımladık
        NavHost(
            navController = navController,
            startDestination = LunchTrayScreen.Start.name,
        ) {
            composable(route = LunchTrayScreen.Start.name) {
                StartOrderScreen(
                    onStartOrderButtonClicked = {
                        navController.navigate(LunchTrayScreen.EntreeMenu.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            composable(route = LunchTrayScreen.EntreeMenu.name) {
                EntreeMenuScreen(
                    options = DataSource.entreeMenuItems,
                    onCancelButtonClicked = {
                        viewModel.resetOrder()
                        navController.popBackStack(LunchTrayScreen.Start.name, inclusive = false)
                    },
                    onNextButtonClicked = {
                        navController.navigate(LunchTrayScreen.SideDishMenu.name)
                    },
                    onSelectionChanged = { item ->
                        viewModel.updateEntree(item)
                    },
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

        }

    }
}

/*
The Start Order button on the Start screen navigates to the Entree menu screen.
The Next button on the Entree menu screen navigates to the Side dish menu screen.
The Next button on the Side dish menu screen navigates to the Accompaniment menu screen.
The Next button on the Accompaniment menu screen navigates to the Checkout screen.
The Submit button on the Checkout screen navigates to the Start screen.
The Cancel button on any screen navigates back to the Start screen.
 */

/**
Bu Composable, topBar'ı görüntüler ve geri navigasyon mümkünse geri düğmesini gösterir.
Mevcut ekran isminde bir argüman ekledik çünkü geri tuşu hep aynı yerde olacak
mevcut ekranı bilecek ve basıldığında önceki ekrana dönecek.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayAppBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        //top app bar'ın ismi mevcut ekranın ismi olacak.
        title = { Text(stringResource(currentScreenTitle)) },
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
        })
}

//Bu fonksiyonu telefondaki back tuşuna bastığımızda olacak işlem için tanımlıyoruz.
//2 adet parametre tanımlayacağız ve amaç şu olacak. Geri gelme tuşuna bastığımızda
//uygulama hangi ekrana dönsün.
private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel, navController: NavHostController
) {
    //En başa geldiğimiz için siparişi sıfırlıyoruz.
    viewModel.resetOrder()
    //
    navController.popBackStack(LunchTrayScreen.Start.name, inclusive = false)
}
