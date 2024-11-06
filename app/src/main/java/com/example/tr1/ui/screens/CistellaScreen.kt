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
import androidx.compose.ui.unit.dp
import com.example.tr1.ui.TakeAwayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarretScreen(navController: NavHostController, viewModel: TakeAwayViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Cistella") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        val cartProducts = viewModel.cartProducts

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (cartProducts.isEmpty()) {
                item {
                    Text(text = "La cistella és buida.", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                items(cartProducts) { product ->
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text(text = product.nomProducte, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Preu producte: ${product.Preu} €", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { viewModel.decrementProductQuantity(product) }) {
                                    Text(text = "-")
                                }
                                Text(text = product.quantity.toString(), style = MaterialTheme.typography.bodyMedium)
                                IconButton(onClick = { viewModel.incrementProductQuantity(product) }) {
                                    Text(text = "+")
                                }
                            }
                            IconButton(
                                onClick = { viewModel.removeProductFromCart(product) },
                                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.error)
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar producto")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Divider()
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
