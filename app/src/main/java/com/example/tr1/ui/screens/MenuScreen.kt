import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.tr1.R
import com.example.tr1.model.Product
import com.example.tr1.ui.TakeAwayApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.tr1.ui.TakeAwayViewModel
import kotlinx.coroutines.delay
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import com.example.tr1.ui.theme.LightOrange
import com.example.tr1.ui.theme.LightRed
import com.example.tr1.ui.theme.LightYellow
import com.example.tr1.ui.theme.LightGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavHostController, products: List<Product>, viewModel: TakeAwayViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.menu)) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = LightOrange, // Color de fondo del TopAppBar
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(TakeAwayApp.Perfil.name) }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Ir a Perfil",
                            tint = LightGreen // Color para el icono del perfil
                        )
                    }
                }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { navController.navigate(TakeAwayApp.Carret.name) },
                        colors = ButtonDefaults.buttonColors(containerColor = LightGreen)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Ir al Carret",
                            modifier = Modifier.size(35.dp).padding(2.dp),
                            tint = Color.Black // Color para el icono del carrito
                        )
                    }
                    AnimatedVisibility(
                        visible = viewModel.cartProducts.isNotEmpty(),
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Text(
                            text = viewModel.cartProducts.size.toString(),
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .offset(x = (58).dp, y = (-10).dp)
                                .background(LightRed, shape = CircleShape) // Fondo para el contador del carrito
                                .padding(8.dp)
                        )
                    }
                    Button(
                        onClick = { navController.navigate(TakeAwayApp.Comandes.name) },
                        colors = ButtonDefaults.buttonColors(containerColor = LightGreen)
                    ) {
                        Icon(
                            imageVector = Icons.Default.List, // Icono de comandos
                            contentDescription = "Ir a Comandes",
                            modifier = Modifier.size(35.dp).padding(2.dp),
                            tint = Color.Black // Color para el icono de comandos
                        )
                    }
                }
            }
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp)
        ) {
            items(products.size) { index ->
                val product = products[index]
                ProductCardScreen(
                    product = product,
                    onClick = { navController.navigate("productScreen/${product.nomProducte}") },
                    viewModel,
                    onAddToCart = { viewModel.addToCart(product) }
                )
            }
        }
    }
}

@Composable
fun ProductCardScreen(product: Product, onClick: () -> Unit, viewModel: TakeAwayViewModel, onAddToCart: () -> Unit) {
    val isInCart = viewModel.isInCart(product)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp)
            .background(LightGreen, shape = MaterialTheme.shapes.medium) // Fondo de la tarjeta de producto
            .padding(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        val imageUrl = "http://10.0.2.2:3010/uploads/images/${product.Imatge}"
        val painter = rememberAsyncImagePainter(
            model = imageUrl,
            contentScale = ContentScale.Crop,
//            placeholder = painterResource(id = R.drawable.ic_launcher_background)
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
            overflow = TextOverflow.Ellipsis,
            color = Color.Black // Color del título del producto
        )
        Text(
            text = product.Descripcio,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black // Color del texto de la descripción
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (product.Stock == 0) "Sin Stock" else "En stock",
            color = if (product.Stock == 0) LightRed else Color.Green, // Color según el stock
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${product.Preu} €",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (!isInCart) {
                    onAddToCart()
                }
            },
            enabled = product.Stock != 0,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (product.Stock == 0) LightRed else LightOrange // Color del botón según disponibilidad
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (isInCart) "Afegit! ✔" else "Afegir", color = Color.White)
        }

    }
}
@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    val products = listOf(
        Product(
            idProducte = 1,
            nomProducte = "Producte 1",
            Descripcio = "Descripció del producte 1",
            Preu = 10.0,
            Stock = 10,
            Imatge = "imatge1.jpg",
        ),
        Product(
            idProducte = 2,
            nomProducte = "Producte 2",
            Descripcio = "Descripció del producte 2",
            Preu = 20.0,
            Stock = 0,
            Imatge = "imatge2.jpg",
        ),
        Product(
            idProducte = 3,
            nomProducte = "Producte 3",
            Descripcio = "Descripció del producte 3",
            Preu = 30.0,
            Stock = 5,
            Imatge = "imatge3.jpg",
        ),
        Product(
            idProducte = 4,
            nomProducte = "Producte 4",
            Descripcio = "Descripció del producte 4",
            Preu = 40.0,
            Stock = 0,
            Imatge = "imatge4.jpg",
        ),
        Product(
            idProducte = 5,
            nomProducte = "Producte 5",
            Descripcio = "Descripció del producte 5",
            Preu = 50.0,
            Stock = 15,
            Imatge = "imatge5.jpg",
        ),
        Product(
            idProducte = 6,
            nomProducte = "Producte 6",
            Descripcio = "Descripció del producte 6",
            Preu = 60.0,
            Stock = 3,
            Imatge = "imatge6.jpg",
        ),
    )
    MenuScreen(navController = NavHostController(LocalContext.current), products = products, viewModel = TakeAwayViewModel())
}

