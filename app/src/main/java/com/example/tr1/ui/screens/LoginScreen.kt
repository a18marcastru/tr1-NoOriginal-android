package com.example.tr1.ui.screens

import android.content.Context
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tr1.R
import com.example.tr1.ui.TakeAwayApp
import com.example.tr1.ui.TakeAwayViewModel

@Composable
fun LoginScreen(navController: NavHostController, context: Context, viewModel: TakeAwayViewModel) {
    // Variables de estado para capturar la entrada del usuario
    var email by remember { mutableStateOf("") }
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
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Correu Electrònic") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para la contraseña
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Contrasenya") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botón de inicio de sesión
        Button(onClick = {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.loginViewModel(email, password)
            }
            else {
                Toast.makeText(context, "Correu o Contrasenya buida", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Iniciar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje de error si el inicio de sesión falla
//        viewModel.loginError.value?.let {
//            Text(
//                text = it,
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para registrarse
        Button(onClick = { navController.navigate(TakeAwayApp.Register.name) }) {
            Text(text = "Registrar")
        }

        LaunchedEffect(viewModel.loginError.value, viewModel.currentUser.value) {
            viewModel.loginError.value?.let { error ->
                // Mostrar mensaje de error en un Toast si loginError contiene un mensaje
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            } ?: viewModel.currentUser.value?.let {
                // Si currentUser tiene un valor, significa que el inicio de sesión fue exitoso
                navController.navigate(TakeAwayApp.Menu.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController(), context = LocalContext.current, viewModel = TakeAwayViewModel())
}