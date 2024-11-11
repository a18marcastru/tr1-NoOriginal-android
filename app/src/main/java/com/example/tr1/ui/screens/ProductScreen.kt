import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.tr1.model.Product
import com.example.tr1.ui.TakeAwayApp
import com.example.tr1.ui.TakeAwayViewModel
import com.example.tr1.ui.theme.LightGreen
import com.example.tr1.ui.theme.LightOrange
import com.example.tr1.ui.theme.LightRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController: NavHostController, product: Product, viewModel: TakeAwayViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Producte") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(TakeAwayApp.Perfil.name) }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Ir a Perfil",
                            tint = LightGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = LightOrange,
                    titleContentColor = Color.White
                )
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
                            tint = Color.Black
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
                                .offset(x = (-20).dp, y = (-10).dp)
                                .background(LightRed, shape = CircleShape)
                                .padding(8.dp)
                        )
                    }
                    Button(
                        onClick = { navController.navigate(TakeAwayApp.Comandes.name) },
                        colors = ButtonDefaults.buttonColors(containerColor = LightGreen)
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Ir a Comandes",
                            modifier = Modifier.size(35.dp).padding(2.dp),
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = product.nomProducte, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.Descripcio, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Precio: \$${product.Preu}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Stock: ${product.Stock}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))

            val imageUrl = "http://10.0.2.2:3010/uploads/images/${product.Imatge}"
            val painter = rememberAsyncImagePainter(
                model = imageUrl,
                contentScale = ContentScale.Crop,
            )
            Log.d("imatge", "Image URL: ${product.Imatge}")
            Image(
                painter = painter,
                contentDescription = product.nomProducte,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.addToCart(product)
            }) {
                Text(text = "Añadir al Carrito")
            }
        }
    }
}