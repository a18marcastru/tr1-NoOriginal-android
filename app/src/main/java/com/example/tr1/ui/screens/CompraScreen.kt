package com.example.tr1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tr1.ui.TakeAwayApp
import com.example.tr1.ui.TakeAwayViewModel
import com.example.tr1.ui.theme.LightOrange
import com.example.tr1.ui.theme.LightYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompraScreen(navController: NavHostController, viewModel: TakeAwayViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Compra") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Tornar")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = LightOrange,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                TicketCard(viewModel)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightOrange
                    )
                ) {
                    Text("Confirmar")
                }
                if (showDialog) {
                    ConfirmationDialog(
                        onDismiss = { showDialog = false },
                        onConfirm = {
                            navController.navigate(TakeAwayApp.Confirmat.name)
                            showDialog = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ConfirmationDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmació") },
        text = { Text("Vols continuar amb la compra?") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightOrange
                )
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightOrange
                )
            ) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun TicketCard(viewModel: TakeAwayViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Ticket",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        for (product in viewModel.cartProducts) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = product.nomProducte,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Quantitat: ${product.quantity}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(
                            modifier = Modifier
                                .background(LightYellow, shape = MaterialTheme.shapes.medium)
                                .padding(horizontal = 8.dp, vertical = 4.dp) // Padding ajustado
                        ) {
                            Text(
                                text = "${product.Preu * product.quantity} €",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .background(LightYellow, shape = MaterialTheme.shapes.medium)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Preu total: ${viewModel.getTotalPrice()} €",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }


    }
}



@Preview(showBackground = true)
@Composable
fun CompraScreenPreview() {
    CompraScreen(navController = NavHostController(LocalContext.current), viewModel = TakeAwayViewModel())
}