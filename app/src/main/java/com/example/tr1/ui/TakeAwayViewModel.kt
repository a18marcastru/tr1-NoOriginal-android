package com.example.tr1.ui

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tr1.data.loadProductsFromApi
import com.example.tr1.data.loadUsuarisFromJson
import com.example.tr1.model.Product
import kotlinx.coroutines.launch

class TakeAwayViewModel() : ViewModel() {

    var products = mutableStateOf<List<Product>?>(null)
        private set

    var loginError = mutableStateOf<String?>(null)
        private set

    var cartProducts = mutableStateListOf<Product>()
        private set

    fun loadProducts() {
        viewModelScope.launch {
            loadProductsFromApi { productesResponse ->
                productesResponse?.let {
                    products.value = it.productes
                }
            }
        }
    }

    fun addToCart(product: Product) {
        cartProducts.add(product)
    }

    fun resetCart() {
        cartProducts.clear()
    }

    fun removeProductFromCart(product: Product) {
        cartProducts.remove(product) // Elimina el producto especificado
    }

    fun login(username: String, password: String, context: Context) {
        val usuaris = loadUsuarisFromJson(context) // Cambia esto a tu forma de cargar usuarios
        val userFound = usuaris?.find { it.Nom == username && it.Contrasenya == password }

        if (userFound != null) {
            loginError.value = null // Reinicia el error al iniciar sesión correctamente
        } else {
            loginError.value = "Usuario o contraseña incorrectos"
        }
    }

    fun resetState() {
        products.value = null
        loginError.value = null
    }
}