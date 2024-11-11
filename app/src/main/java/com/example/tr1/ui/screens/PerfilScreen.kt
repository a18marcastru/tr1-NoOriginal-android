import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
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
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.tr1.model.UpdateUserRequest
import com.example.tr1.ui.TakeAwayApp
import com.example.tr1.ui.TakeAwayViewModel
import com.example.tr1.ui.theme.LightOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavHostController, context: Context, viewModel: TakeAwayViewModel) {
    var isEditingName by remember { mutableStateOf(false) }
    var isEditingPassword by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(viewModel.currentUser.value?.Nom ?: "") }
    var email by remember { mutableStateOf(viewModel.currentUser.value?.Correu ?: "") }
    var newPassword by remember { mutableStateOf("") }
    var repeatNewPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = LightOrange,
                    titleContentColor = Color.White
                )
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom") },
                    enabled = isEditingName,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { isEditingName = !isEditingName }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                if (!isEditingPassword) {
                    Button(onClick = {
                        isEditingPassword = true
                    }) {
                        Text(text = "Modificar Contrasenya")
                    }
                }
            }
            if (isEditingPassword) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("Nova Contrasenya") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = repeatNewPassword,
                        onValueChange = { repeatNewPassword = it },
                        label = { Text("Repetir Nova Contrasenya") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            isEditingPassword = false
                            newPassword = ""
                            repeatNewPassword = ""
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Cancelar")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Button(onClick = {
                    if (isEditingPassword && newPassword != repeatNewPassword) {
                        Toast.makeText(context, "Les contrasenyes no coincideixen", Toast.LENGTH_SHORT).show()
                    } else {
                        updateUser(name, email, if (isEditingPassword) newPassword else null, viewModel)
                        isEditingPassword = false
                        isEditingName = false
                    }
                }) {
                    Text(text = "Guardar canvis")
                }

                Button(onClick = {
                    viewModel.logout()
                    navController.navigate(TakeAwayApp.Login.name) {
                        popUpTo(TakeAwayApp.Login.name) { inclusive = true }
                    }
                }) {
                    Text(text = "LogOut")
                }

                LaunchedEffect(viewModel.updateInfo.value) {
                    viewModel.updateInfo.value?.let { message ->
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        viewModel.updateInfo.value = null
                    }
                }
            }
        }
    }
}

fun updateUser(newName: String, newCorreu: String, newPass: String?, viewModel: TakeAwayViewModel){
    val dataUser = if (newPass.isNullOrEmpty()) {
        viewModel.currentUser.value?.let {
            UpdateUserRequest(
                Nom = newName,
                Correu = newCorreu,
                Contrasenya = it.Contrasenya
            )
        }
    } else {
        val hashedPassword = viewModel.hashPassword(newPass)
        UpdateUserRequest(
            Nom = newName,
            Correu = newCorreu,
            Contrasenya = hashedPassword
        )
    }

    dataUser?.let {
        Log.d("currentUser", "$it")
        viewModel.updateUserViewModel(it)
    }
}