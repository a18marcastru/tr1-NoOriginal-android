package com.example.tr1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.tr1.model.Product

@Composable
fun MenuScreen(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Cargar y mostrar la imagen del producto
        val painter = rememberAsyncImagePainter(model = product.Imatge)
        Image(
            painter = painter,
            contentDescription = product.nomProducte,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar el nombre del producto
        Text(
            text = product.nomProducte,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Mostrar la descripción del producto
        Text(
            text = product.Descripcio,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        // Mostrar el precio del producto
        Text(
            text = "Precio: \$${product.Preu}",
            style = MaterialTheme.typography.bodyMedium
        )

        // Mostrar el stock del producto
        Text(
            text = "Stock: ${product.Stock}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}