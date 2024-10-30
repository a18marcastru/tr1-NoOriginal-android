package com.example.tr1.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tr1.R
import com.example.tr1.model.Comanda
import com.example.tr1.model.EstatComanda
import com.example.tr1.model.Product
import com.example.tr1.ui.TakeAwayApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComandesScreen(navController: NavHostController, commands: List<Comanda>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.comandes)) }
            )
        },
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { navController.navigate(TakeAwayApp.Carret.name) }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Ir al Carret"
                    )
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(commands) { command ->
                ComandaItem(command)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ComandaItem(comanda: Comanda) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "nomUsuariExemple",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "Preu Total: ${comanda.preuTotal}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Estat: ${comanda.estat}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Data: ${comanda.data}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComandesScreenPreview() {
    val comanda1 = Comanda(
        idComanda = 1,
        idUsuari = 123,
        productes = listOf(
            Product(
                idProducte = 1.toString(),
                nomProducte = "Pizza Margarita",
                Descripcio = "Deliciosa pizza con tomate, mozzarella y albahaca.",
                Preu = 10.50,
                Stock = 20,
                Imatge = "https://example.com/pizza-margarita.jpg"
            ),
            Product(
                idProducte = 2.toString(),
                nomProducte = "Refresco Cola",
                Descripcio = "Refrescante bebida de cola.",
                Preu = 2.00,
                Stock = 50,
                Imatge = "https://example.com/refresco-cola.jpg"
            )
        ),
        preuTotal = 12.50,
        estat = EstatComanda.PENDENT_DE_PREPARACIO,
        data = "2023-10-27T10:00:00"
    )

    val comanda2 = Comanda(
        idComanda = 1,
        idUsuari = 123,
        productes = listOf(
            Product(
                idProducte = 1.toString(),
                nomProducte = "Pizza Margarita",
                Descripcio = "Deliciosa pizza con tomate, mozzarella y albahaca.",
                Preu = 10.50,
                Stock = 20,
                Imatge = "https://example.com/pizza-margarita.jpg"
            ),
            Product(
                idProducte = 2.toString(),
                nomProducte = "Refresco Cola",
                Descripcio = "Refrescante bebida de cola.",
                Preu = 2.00,
                Stock = 50,
                Imatge = "https://example.com/refresco-cola.jpg"
            )
        ),
        preuTotal = 12.50,
        estat = EstatComanda.PENDENT_DE_PREPARACIO,
        data = "2023-10-27T10:00:00"
    )
    ComandesScreen(navController = rememberNavController(), commands = listOf(comanda1, comanda2))
}