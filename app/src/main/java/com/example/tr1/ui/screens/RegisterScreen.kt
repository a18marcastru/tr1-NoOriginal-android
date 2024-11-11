package com.example.tr1.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.tr1.ui.TakeAwayApp
import com.example.tr1.ui.TakeAwayViewModel
import com.example.tr1.ui.theme.LightOrange


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController, context: Context, viewModel: TakeAwayViewModel) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Registre") },
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Registre",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(text = "Usuari") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Correu") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Contrasenya") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = confirmpassword,
                onValueChange = { confirmpassword = it },
                label = { Text(text = "Confirmar contrasenya") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        if (password == confirmpassword) {
                            viewModel.registerViewModel(username, email, password)
                        } else {
                            Toast.makeText(context, "Les contrasenyes no coincideixen", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Correu o Contrasenya buida", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightOrange
                )
            ) {
                Text(text = "Registrar-se")
            }

            LaunchedEffect(viewModel.loginError.value, viewModel.currentUser.value) {
                viewModel.loginError.value?.let { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                } ?: viewModel.currentUser.value?.let {
                    navController.navigate(TakeAwayApp.Menu.name) {
                        popUpTo(TakeAwayApp.Register.name) { inclusive = true }
                    }
                }
            }
        }
    }
}