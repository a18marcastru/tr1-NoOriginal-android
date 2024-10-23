package com.example.tr1.ui

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tr1.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import coil.compose.rememberAsyncImagePainter
import coil.transform.CircleCropTransformation

enum class TakeAwayApp(@StringRes val title: Int) {
    Login(title =R.string.login),
    Menu(title = R.string.menu),
    Perfil(title = R.string.perfil),
    Product(title = R.string.producte),
    Carret(title = R.string.carret),
    Compra(title = R.string.compra)
}

@Composable
fun TakeAwayApp(navController: NavHostController) {
    NavHost(navController, startDestination = TakeAwayApp.Login.name) {
        composable(route = TakeAwayApp.Login.name) {
            LoginScreen(onStartClicked = {
                // Navegar al menú después de hacer clic en "Iniciar"
                navController.navigate(TakeAwayApp.Menu.name)
            })
        }
        composable(route = TakeAwayApp.Menu.name) {
            MenuScreen(navController)
        }
        composable(route = TakeAwayApp.Perfil.name) {
            PerfilScreen(navController)
        }
        composable(route = TakeAwayApp.Product.name) {
            ProductScreen(navController)
        }
        composable(route = TakeAwayApp.Carret.name) {
            CarretScreen(navController)
        }
        composable(route = TakeAwayApp.Compra.name) {
            CompraScreen(navController)  // Navegar a la nueva pantalla de compra
        }
    }
}

// Pantalla de login
@Composable
fun LoginScreen(onStartClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.login),  // Usar stringResource con el ID del string
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onStartClicked) {
            Text(text = "Iniciar")  // Asume que tienes un recurso de cadena para "Iniciar"
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.menu)) },
                actions = {
                    // Botón de Perfil en la esquina superior derecha
                    IconButton(onClick = { navController.navigate(TakeAwayApp.Perfil.name) }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,  // Icono de perfil
                            contentDescription = "Ir a Perfil"
                        )
                    }
                }
            )
        }
    ) { padding ->
        // Contenido del menú en el cuerpo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),  // Respeta el padding del Scaffold
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // URL de la imagen
            val imageUrl = "https://www.zitromac.com/wp-content/uploads/2021/08/Naranja_zumo.jpg" // Reemplaza con tu URL

            // Botón de imagen para ir a productos
            IconButton(onClick = { navController.navigate(TakeAwayApp.Product.name) }) {
                // Cargar la imagen desde la URL
                Image(
                    painter = rememberAsyncImagePainter(
                        model = imageUrl
                    ),
                    contentDescription = "Ir a Producto",
                    modifier = Modifier.size(120.dp) // Tamaño de la imagen
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botón de texto para ir al carrito
            Button(onClick = { navController.navigate(TakeAwayApp.Carret.name) }) {
                Text(text = "Ir a Carret")
            }
        }
    }
}

// Pantalla de Perfil con botón de retroceso
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Perfil",  // Asume que tienes un recurso de cadena para el menú
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

// Pantalla de Productos con botón de retroceso
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Productos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Productos",  // Asume que tienes un recurso de cadena para el menú
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

// Pantalla de Carret con botón de retroceso
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarretScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Carret") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Carret",  // Asume que tienes un recurso de cadena para el menú
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(TakeAwayApp.Compra.name) }) {
                Text("Comprar") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompraScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Compra") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Compra",  // Título de la pantalla de compra
                style = MaterialTheme.typography.titleLarge
            )
            // Aquí puedes agregar más contenido relacionado con la compra
        }
    }
}
