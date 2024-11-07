package com.example.tr1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.tr1.ui.TakeAwayViewModel
import com.example.tr1.ui.theme.LightGreen
import com.example.tr1.ui.theme.LightOrange
import com.example.tr1.ui.theme.LightRed
import com.example.tr1.ui.theme.LightWhite

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
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = LightOrange, // Color de fondo del TopAppBar
                    titleContentColor = Color.White
                )
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
                    Text(
                        text = "La cistella és buida.",
                        modifier = Modifier.padding(vertical = 16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                items(cartProducts) { product ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(LightWhite, shape = MaterialTheme.shapes.medium)
                            .padding(12.dp)
                    ) {
                        val imageUrl = "http://10.0.2.2:3010/uploads/images/${product.Imatge}"
                        val painter = rememberAsyncImagePainter(model = imageUrl, contentScale = ContentScale.Crop)
                        Image(
                            painter = painter,
                            contentDescription = product.nomProducte,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(end = 8.dp)
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = product.nomProducte,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${product.Preu} €",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { viewModel.decrementProductQuantity(product) }) {
                                        Text(text = "-", color = Color.Black)
                                    }
                                    Text(text = product.quantity.toString(), style = MaterialTheme.typography.bodyMedium)
                                    IconButton(onClick = { viewModel.incrementProductQuantity(product) }) {
                                        Text(text = "+", color = Color.Black)
                                    }
                                }
                                IconButton(
                                    onClick = { viewModel.removeProductFromCart(product) },
                                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar producto")
                                }
                            }
                        }
                    }
                    Divider()
                }
                item {
                    Button(
                        onClick = {
                            viewModel.resetCart() // Reinicia el carrito
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = LightOrange),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text(text = "Reiniciar Carrito", color = Color.White)
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