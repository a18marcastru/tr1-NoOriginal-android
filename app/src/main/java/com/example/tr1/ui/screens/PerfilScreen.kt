import androidx.compose.runtime.Composable
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
import com.example.tr1.ui.TakeAwayApp
import com.example.tr1.ui.TakeAwayViewModel

// Pantalla de Perfil
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavHostController, viewModel: TakeAwayViewModel) {
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
            Button(onClick = { navController.navigate(TakeAwayApp.Comandes.name) }) {
                Text(text = "Historial de comandes")
            }
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