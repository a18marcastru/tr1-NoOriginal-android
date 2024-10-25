package com.example.tr1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.tr1.model.Product
import com.example.tr1.ui.ComandaViewModel
import com.example.tr1.ui.TakeAwayApp

// Pantalla de Carret
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarretScreen(navController: NavHostController, comandaViewModel: ComandaViewModel) {
    val cartItems = comandaViewModel.cistella // Access cart items from ViewModel

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
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Carret",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (cartItems.isEmpty()) {
                Text("Your cart is empty.")
            } else {
                LazyColumn {
                    items(cartItems) { product ->
                        CartItem(product)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = { navController.navigate(TakeAwayApp.Compra.name) }) {
                Text("Comprar")
            }
        }
    }
}


@Composable
fun CartItem(product: Product) {
    val painter = rememberAsyncImagePainter(model = product.Imatge)
    Image(
        painter = painter,
        contentDescription = product.nomProducte,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = product.nomProducte,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
    Text(
        text = product.Descripcio,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
    Text(
        text = "Precio: \$${product.Preu}",
        style = MaterialTheme.typography.bodyMedium
    )
    Text(
        text = "Stock: ${product.Stock}",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Preview(showBackground = true)
@Composable
fun CarretScreenPreview() {
    val navController = rememberNavController()
    val comandaViewModel = ComandaViewModel()

    comandaViewModel.addToCart(Product(nomProducte = "Product 1", Descripcio = "Description 1", Preu = 10.0, Stock = 5, Imatge = ""))
    comandaViewModel.addToCart(Product(nomProducte = "Product 2", Descripcio = "Description 2", Preu = 20.0, Stock = 3, Imatge = ""))

    CarretScreen(navController, comandaViewModel)
}
