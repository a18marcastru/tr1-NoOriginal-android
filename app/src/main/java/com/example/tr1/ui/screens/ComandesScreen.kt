package com.example.tr1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tr1.R
import com.example.tr1.model.Comanda
import com.example.tr1.model.EstatComanda
import com.example.tr1.model.ProductePerComanda
import com.example.tr1.ui.TakeAwayApp
import com.example.tr1.ui.theme.LightGreen
import com.example.tr1.ui.theme.LightOrange
import com.example.tr1.ui.theme.LightWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComandesScreen(navController: NavHostController, commands: List<Comanda>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.comandes)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Tornar")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(TakeAwayApp.Perfil.name) }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Ir a Perfil",
                            tint = LightGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = LightOrange,
                    titleContentColor = Color.White
                )
            )
        },

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
            .background(LightGreen, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Productes:")
        comanda.Productes.forEach { producte ->
            Text(text = "Nom: ${producte.nomProducte}")
            Text(text = "Quantitat: ${producte.quantitat}")
            Text(text = "Preu Total del Producte: ${producte.preuTotalProducte}")
            Spacer(modifier = Modifier.height(8.dp))
        }
        Text(
            text = "Preu Total: ${comanda.PreuTotal}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Estat: ${comanda.Estat}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Data: ${comanda.Data}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ComandesScreenPreview() {
    val comanda1 = Comanda(
        idComanda = 1,
        idUsuari = 1,
        Productes = listOf(
            ProductePerComanda(
                idProducte = 1,
                nomProducte = "Hamburguesa",
                quantitat = 1,
                preuTotalProducte = 8.00
            ),
            ProductePerComanda(
                idProducte = 1,
                nomProducte = "Hamburguesa",
                quantitat = 1,
                preuTotalProducte = 8.00
            )
        ),
        PreuTotal = 16.50,
        Estat = EstatComanda.EN_PREPARACIO,
        Data = "2023-11-19"
    )

    val comanda2 = Comanda(
        idComanda = 2,
        idUsuari = 1,
        Productes = listOf(
            ProductePerComanda(
                idProducte = 1,
                nomProducte = "Hamburguesa",
                quantitat = 1,
                preuTotalProducte = 8.00
            ),
            ProductePerComanda(
                idProducte = 1,
                nomProducte = "Hamburguesa",
                quantitat = 1,
                preuTotalProducte = 8.00
            )
        ),
        PreuTotal = 11.50,
        Estat = EstatComanda.RECOLLIT,
        Data = "2023-11-19"
    )
    ComandesScreen(navController = rememberNavController(), commands = listOf(comanda1, comanda2))
}