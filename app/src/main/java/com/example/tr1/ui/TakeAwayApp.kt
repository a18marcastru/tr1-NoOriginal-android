// ui/TakeAwayApp.kt
package com.example.tr1.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.tr1.data.loadProductsFromJson
import com.example.tr1.ui.screens.MenuScreen

@Composable
fun TakeAwayApp(context: Context) {
    // Cargar productos desde el archivo JSON en res/raw
    val products = remember { loadProductsFromJson(context) }

    // Mostrar la lista de productos
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(products ?: emptyList()) { product ->
            MenuScreen(product)
        }
    }
}