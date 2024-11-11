package com.example.tr1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tr1.model.Comanda
import com.example.tr1.model.EstatComanda
import com.example.tr1.ui.TakeAwayApp
import com.example.tr1.ui.TakeAwayViewModel
import com.example.tr1.ui.theme.LightOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmatScreen(navController: NavHostController, viewModel: TakeAwayViewModel) {
    LaunchedEffect(Unit) {
        enviarNovaComanda(viewModel)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Estat de la compra") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = LightOrange,
                    titleContentColor = Color.White
                )
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
                text = "Compra completada amb èxit!",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate(TakeAwayApp.Menu.name) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightOrange
                )
            ) {
                Text("Tornar al menu")
            }
        }
    }
}

fun enviarNovaComanda(viewModel: TakeAwayViewModel) {
    val comanda = Comanda(
        idComanda = 0,
        idUsuari = viewModel.getIdUsuari() ?: 0,
        Data = "",
        Estat = EstatComanda.PENDENT_DE_PREPARACIO,
        Productes = viewModel.transformProductsToOrderProducts(viewModel.cartProducts),
        PreuTotal = viewModel.getTotalPrice()
    )
    viewModel.resetCart()
    viewModel.novaComanda(comanda)
}

@Preview(showBackground = true)
@Composable
fun ConfirmatScreenPreview() {
    ConfirmatScreen(navController = NavHostController(LocalContext.current), viewModel = TakeAwayViewModel())
}