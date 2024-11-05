import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.tr1.R
import com.example.tr1.model.Product
import com.example.tr1.ui.TakeAwayApp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.tr1.ui.TakeAwayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavHostController, products: List<Product>, viewModel: TakeAwayViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.menu)) },
                actions = {
                    IconButton(onClick = { navController.navigate(TakeAwayApp.Perfil.name) }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Ir a Perfil"
                        )
                    }
                }
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(products.size) { index ->
                val product = products[index]
                ProductCardScreen(product = product, onClick = {
                    navController.navigate("productScreen/${product.nomProducte}")
                }, viewModel)
                }
            }
        }
    }


@Composable
fun ProductCardScreen(product: Product, onClick: () -> Unit, viewModel: TakeAwayViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        val imageUrl = "http://juicengo.dam.inspedralbes.cat:20871/uploads/images/${product.Imatge}"
        val painter = rememberAsyncImagePainter(
            model = imageUrl,
            contentScale = ContentScale.Crop, // Adjust scaling as needed
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            //error = painterResource(id = R.drawable.error_image) // Add an error image
        )
        Log.d("imatge", "Image URL: ${product.Imatge}")
        Image(
            painter = painter,
            contentDescription = product.nomProducte,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.nomProducte,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = product.Descripcio,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (product.Stock == 0) {
            Text(
                text = "Sin Stock",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }else{
            Text(
                text = "En stock",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Green
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Precio: \$${product.Preu}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.addToCart(product) },
            enabled = product.Stock != 0,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (product.Stock == 0) Color.Red else MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Afegir")
        }
        }
    }
