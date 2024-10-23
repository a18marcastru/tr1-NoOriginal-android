import android.media.Image
import androidx.compose.runtime.Composable
import com.example.tr1.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.menu)) },
                actions = {
                    IconButton(onClick = { navController.navigate("asd") }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Ir a Perfil"
                        )
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
            Spacer(modifier = Modifier.height(16.dp))

            val imageUrl = "https://www.zitromac.com/wp-content/uploads/2021/08/Naranja_zumo.jpg" // Reemplaza con tu URL


            IconButton(onClick = { navController.navigate("aaa") }) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = imageUrl
                    ),
                    contentDescription = "Ir a Producto",
                    modifier = Modifier.size(120.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.navigate("aaa") }) {
                Text(text = "Ir a Carret")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    val navController = rememberNavController()
    MenuScreen(navController = navController)
}

