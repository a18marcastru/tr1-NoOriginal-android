package com.example.tr1.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tr1.R
import com.example.tr1.ui.TakeAwayApp
import com.example.tr1.ui.TakeAwayViewModel

@Composable
fun LoginScreen(navController: NavHostController, context: Context, viewModel: TakeAwayViewModel) {
    // Variables de estado para capturar la entrada del usuario
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para el nombre de usuario
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la contraseña
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botón de inicio de sesión
        Button(onClick = {
            viewModel.login(username, password, context)
            if (viewModel.loginError.value == null) {
                // Iniciar sesión y navegar a la pantalla del menú
                navController.navigate(TakeAwayApp.Menu.name)
            }
        }) {
            Text(text = "Iniciar")
        }

        // Mensaje de error si el inicio de sesión falla
        viewModel.loginError.value?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para registrarse
        Button(onClick = { navController.navigate(TakeAwayApp.Register.name) }) {
            Text(text = "Registrar")
        }
    }
}