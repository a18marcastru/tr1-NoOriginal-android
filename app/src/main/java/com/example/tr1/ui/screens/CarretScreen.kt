package com.example.tr1.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.tr1.ui.TakeAwayApp
import com.example.tr1.ui.TakeAwayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarretScreen(navController: NavHostController, viewModel: TakeAwayViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Carrito") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        // Obtener los productos en el carrito
        val cartProducts = viewModel.cartProducts

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Comprobar si hay productos en el carrito
            if (cartProducts.isEmpty()) {
                item {
                    Text(text = "El carrito está vacío.", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                // Mostrar los productos en el carrito
                items(cartProducts) { product ->
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text(text = product.nomProducte, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Preu producte: ${product.Preu} €", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))

                        // Row for quantity control and delete icon
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Quantity control
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { viewModel.decrementProductQuantity(product) }) {
                                    Text(text = "-")
                                }
                                Text(text = product.quantity.toString(), style = MaterialTheme.typography.bodyMedium)
                                IconButton(onClick = { viewModel.incrementProductQuantity(product) }) {
                                    Text(text = "+")
                                }
                            }
                            // Delete icon
                            IconButton(
                                onClick = { viewModel.removeProductFromCart(product) },
                                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.error)
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar producto")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Divider() // Un separador entre productos
                    }
                }
                item {
                    Button(
                        onClick = {
                            viewModel.resetCart() // Reinicia el carrito
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text(text = "Reiniciar Carrito")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Precio total del pedido: ${viewModel.getTotalPrice()} €",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}