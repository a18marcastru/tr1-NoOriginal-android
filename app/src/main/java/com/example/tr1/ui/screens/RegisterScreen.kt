package com.example.tr1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tr1.ui.TakeAwayApp


@Composable
fun RegisterScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Registro",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = mail,
            onValueChange = { mail = it },
            label = { Text(text = "Correo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = confirmpassword,
            onValueChange = { confirmpassword = it },
            label = { Text(text = "Confirmar contrasenya") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Button(onClick = { navController.navigate(TakeAwayApp.Menu.name) }) {
            Text(text = "Registrar")
        }
    }
}