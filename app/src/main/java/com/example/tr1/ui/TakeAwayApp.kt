package com.example.tr1.ui

import MenuScreen
import PerfilScreen
import ProductScreen
import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tr1.R
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.tr1.ui.screens.CistellaScreen
import com.example.tr1.ui.screens.ComandesScreen
import com.example.tr1.ui.screens.CompraScreen
import com.example.tr1.ui.screens.ConfirmatScreen
import com.example.tr1.ui.screens.LoginScreen
import com.example.tr1.ui.screens.RegisterScreen

enum class TakeAwayApp(@StringRes val title: Int) {
    Login(title = R.string.login),
    Register(title = R.string.registrar),
    Menu(title = R.string.menu),
    Perfil(title = R.string.perfil),
    Comandes(title = R.string.comandes),
    Carret(title = R.string.carret),
    Compra(title = R.string.compra),
    Confirmat(title = R.string.confirmat)

}

@Composable
fun TakeAwayApp(navController: NavHostController, context: Context) {
    // Inicializar el ViewModel
    val viewModel: TakeAwayViewModel = viewModel()

    // Cargar productos desde la API cuando la app se inicia
    LaunchedEffect(Unit) {
        viewModel.loadProducts()
        viewModel.comandes.value = emptyList()
    }

    NavHost(navController, startDestination = TakeAwayApp.Login.name) {
        composable(route = TakeAwayApp.Login.name) {
            LoginScreen(navController, context, viewModel)
        }
        composable(route = TakeAwayApp.Register.name) {
            RegisterScreen(navController, context, viewModel)
        }
        composable(route = TakeAwayApp.Menu.name) {
            val products = viewModel.products.value
            if (products != null) {
                MenuScreen(navController, products, viewModel) // Pasar el ViewModel aquí
            } else {
                Text("Carregant productes...")
            }
        }
        composable(route = TakeAwayApp.Perfil.name) {
            PerfilScreen(navController, context, viewModel)
        }
        composable(route = TakeAwayApp.Comandes.name) {
            LaunchedEffect(Unit) {
                viewModel.loadComandes()
                Log.d("comandes", "Comandes: ${viewModel.comandes.value}")
            }
            val comandes = viewModel.comandes.value
            if (comandes != null) {
                ComandesScreen(navController, comandes)
            }

        }
        composable(route = "productScreen/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val products = viewModel.products.value
            val selectedProduct = products?.find { it.nomProducte == productId }
            if (selectedProduct != null) {
                ProductScreen(navController, selectedProduct, viewModel) // Pasar el ViewModel aquí
            } else {
                Text("Producte no trobat")
            }
        }
        composable(route = TakeAwayApp.Carret.name) {
            CistellaScreen(navController, viewModel) // Pasar el ViewModel aquí también
        }
        composable(route = TakeAwayApp.Compra.name) {
            CompraScreen(navController, viewModel)
        }
        composable(route = TakeAwayApp.Confirmat.name) {
            ConfirmatScreen(navController, viewModel)
        }
    }
}