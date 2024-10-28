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

    fun incrementProductQuantity(product: Product) {
        val index = cartProducts.indexOf(product)

        if(index != -1){
            cartProducts[index] = cartProducts[index].copy(quantity = cartProducts[index].quantity + 1)
        }
    }

    fun decrementProductQuantity(product: Product) {
        val index = cartProducts.indexOf(product)

        if(index != 1 && cartProducts[index].quantity > 1){
            cartProducts[index] = cartProducts[index].copy(quantity = cartProducts[index].quantity - 1)
        }
    }

    fun addToCart(product: Product) {
        val existingProduct = cartProducts.find { it.nomProducte == product.nomProducte }
        if(existingProduct != null){
            incrementProductQuantity(existingProduct)
        }else{
            cartProducts.add(product.copy(quantity = 1))
        }

    }

    fun resetCart() {
        cartProducts.clear()
    }

    fun getTotalPrice(): Double{
        return cartProducts.sumOf { it.Preu * it.quantity }
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