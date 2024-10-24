package com.example.tr1.ui

import MenuScreen
import PerfilScreen
import ProductScreen
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tr1.R
import androidx.navigation.NavHostController
import com.example.tr1.model.Product
import com.example.tr1.data.loadProductsFromApi
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.tr1.ui.screens.CarretScreen
import com.example.tr1.ui.screens.CompraScreen
import com.example.tr1.ui.screens.ConfirmatScreen
import com.example.tr1.ui.screens.LoginScreen
import com.example.tr1.ui.screens.RegisterScreen

enum class TakeAwayApp(@StringRes val title: Int) {
    Login(title = R.string.login),
    Register(title = R.string.registrar),
    Menu(title = R.string.menu),
    Perfil(title = R.string.perfil),
    Carret(title = R.string.carret),
    Compra(title = R.string.compra),
    Confirmat(title = R.string.confirmat)

}

@Composable
fun TakeAwayApp(navController: NavHostController, context: Context) {
    // Cargar productos desde el archivo JSON
    var products by remember { mutableStateOf<List<Product>>(emptyList()) } // Manejo de nulos

    LaunchedEffect(Unit) {
        loadProductsFromApi { productesResponse ->
            if (productesResponse != null) {
                products = productesResponse.productes  // Actualizar productos si la respuesta es exitosa
            }
        }
    }

    NavHost(navController, startDestination = TakeAwayApp.Login.name) {
        composable(route = TakeAwayApp.Login.name) {
            LoginScreen(navController)
        }
        composable(route = TakeAwayApp.Register.name) {
            RegisterScreen(navController)
        }
        composable(route = TakeAwayApp.Menu.name) {
            MenuScreen(navController, products) // Pasar la lista de productos
            if (products != null) {
                MenuScreen(navController, products)
            } else {
                // Mostrar un mensaje de carga mientras los productos se obtienen
                Text("Cargando productos...")
            }
        }
        composable(route = TakeAwayApp.Perfil.name) {
            PerfilScreen(navController)
        }
        composable(
            route = "productScreen/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val selectedProduct = products?.find { it.nomProducte == productId }
            if (selectedProduct != null) {
                ProductScreen(navController, selectedProduct)
            } else {
                Text("Producto no encontrado")
            }
        }
        composable(route = TakeAwayApp.Carret.name) {
            CarretScreen(navController)
        }
        composable(route = TakeAwayApp.Compra.name) {
            CompraScreen(navController)  // Navegar a la nueva pantalla de compra
        }
        composable(route = TakeAwayApp.Confirmat.name){
            ConfirmatScreen(navController)
        }
    }
}

