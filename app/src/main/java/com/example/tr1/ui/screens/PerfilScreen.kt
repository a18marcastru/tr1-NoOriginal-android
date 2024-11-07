import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.tr1.ui.TakeAwayApp
import com.example.tr1.ui.TakeAwayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavHostController, viewModel: TakeAwayViewModel) {
    var isEditingName by remember { mutableStateOf(false) }
    var isEditingEmail by remember { mutableStateOf(false) }
    var isEditingPassword by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(viewModel.currentUser.value?.Nom ?: "") }
    var email by remember { mutableStateOf(viewModel.currentUser.value?.Correu ?: "") }
    var password by remember { mutableStateOf(viewModel.currentUser.value?.Contrasenya ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Perfil",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                enabled = isEditingName,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
            IconButton(onClick = { isEditingName = !isEditingName }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Gmail") },
                enabled = isEditingEmail,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
            IconButton(onClick = { isEditingEmail = !isEditingEmail }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
            }
            Spacer(modifier = Modifier.height(8.dp))
//            Button(onClick = {
//
//                TextField(
//                    value = "",
//                    onValueChange = { password = it },
//                    label = { Text("Contraseña") },
//                    enabled = isEditingPassword,
//                    visualTransformation = PasswordVisualTransformation(),
//                )
//            }) {
//                Text(text = "Guardar")
//            }
//
//            )
            IconButton(onClick = { isEditingPassword = !isEditingPassword }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Button(onClick = {
                    // Limpiar los datos de sesión
                    viewModel.logout()
                    // Navegar a la pantalla de login
                    navController.navigate(TakeAwayApp.Login.name) {
                        // Asegúrate de limpiar el backstack para que no puedas volver a la pantalla de perfil después de hacer log out
                        popUpTo(TakeAwayApp.Login.name) { inclusive = true }
                    }
                }) {
                    Text(text = "LogOut")
                }
            }
        }
    }
}