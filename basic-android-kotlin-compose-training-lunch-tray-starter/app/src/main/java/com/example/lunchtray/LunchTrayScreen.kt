package com.example.lunchtray

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.ui.OrderViewModel

// TODO: Screen enum
//Buradaki güncelleme işlemi yapıldı. top bar da gözükecek.
enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    EntreeMenu(title = R.string.choose_entree),
    SideDishMenu(title = R.string.choose_side_dish),
    AccompanimentMenu(title = R.string.choose_accompaniment),
    Checkout(title = R.string.order_checkout)
}

// TODO: AppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // TODO: Create Controller and initialization
    //Top app bar'daki geri tuşu için uygulamada önceki sayfa varmı bakacağız
    //eğer ilk ekranda ise geri tuşumuz gözükmeyecek.
    val backStackEntry by navController.currentBackStackEntryAsState()
    //mevcut ekranın değerini güvenli bir şekilde belirlemek ve
    // onu currentScreen değişkenine atamaktır.
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )

    // Create ViewModel
    val viewModel: OrderViewModel = viewModel()

    Scaffold(
        topBar = {
            // TODO: AppBar
            LunchTrayAppBar(
                currentScreen = currentScreen,
                canNavigateBack =  navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                })
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        // TODO: Navigation host
    }
}

/**
Bu Composable, topBar'ı görüntüler ve geri navigasyon mümkünse geri düğmesini gösterir.
Mevcut ekran isminde bir argüman ekledik çünkü geri tuşu hep aynı yerde olacak
mevcut ekranı bilecek ve basıldığında önceki ekrana dönecek.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        //top app bar'ın ismi mevcut ekranın ismi olacak.
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(),
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
