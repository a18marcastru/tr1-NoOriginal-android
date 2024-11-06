package com.example.tr1.ui

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tr1.data.loadComandesFromApi
import com.example.tr1.data.loadProductsFromApi
import com.example.tr1.data.login
import com.example.tr1.model.Comanda
import com.example.tr1.model.LoginRequest
import com.example.tr1.model.Product
import com.example.tr1.model.Usuari
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.launch

class TakeAwayViewModel() : ViewModel() {

    var products = mutableStateOf<List<Product>?>(null)
        private set

    var loginError = mutableStateOf<String?>(null)
        private set

    var cartProducts = mutableStateListOf<Product>()
        private set

    var comandes = mutableStateOf<List<Comanda>?>(null)
        private set

    var currentUser = mutableStateOf<Usuari?>(null)
        private set

    lateinit var mSocket: Socket

     //Creació de socket
    init {
        viewModelScope.launch {
            try {
                //mSocket = IO.socket("http://10.0.2.2:3010")
                mSocket = IO.socket("http://juicengo.dam.inspedralbes.cat:20871")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("SocketIO", "Failed to connect to socket", e)
            }
            mSocket.connect()
            mSocket.on(Socket.EVENT_CONNECT) {
                Log.d("SocketIO", "Connected to socket: ${mSocket.id()}")
                mSocket.on("new-product", onNewProduct)
                mSocket.on("delete-product", onDeleteProduct)
                mSocket.on("update-product", onUpdatedProduct)
            }
            mSocket.on(Socket.EVENT_DISCONNECT) {
                Log.d("SocketIO", "Disconnected from socket")
            }
        }
    }

    //Events de socket

    // En crear un producte
    private val onNewProduct = Emitter.Listener { args ->
        val productJson = args[0] as String
        Log.d("SocketIO", "Producte nou: $productJson")
        val newProduct = Gson().fromJson(productJson, Product::class.java)
        products.value = products.value?.toMutableList()?.apply {
            add(newProduct)
        } ?: listOf(newProduct)
        Log.d("SocketIO", "Producte afegit: $newProduct")
    }

    // En esborrar un producte
    private val onDeleteProduct = Emitter.Listener { args ->
        val productId = args[0] as String
        val id = Gson().fromJson(productId, Int::class.java)
        val deletedProduct = products.value?.find { it.idProducte == id }
        products.value = products.value?.filterNot { it.idProducte == id }
        Log.d("SocketIO", "Deleted product: $deletedProduct")
    }

    // En actualitzar un producte
    private val onUpdatedProduct = Emitter.Listener { args ->
        val productJson = args[0] as String
        Log.d("SocketIO", "Producte a actualitzar: $productJson")
        val updatedProduct = Gson().fromJson(productJson, Product::class.java)
        products.value = products.value?.map { existingProduct ->
            if (existingProduct.idProducte == updatedProduct.idProducte) {
                updatedProduct
            } else {
                existingProduct
            }
        }
        Log.d("SocketIO", "Updated product: $updatedProduct")
    }


    override fun onCleared() {
        super.onCleared()
        mSocket.disconnect()
        Log.d("SocketIO", "Disconnected from socket")
    }

    fun loadProducts() {
        viewModelScope.launch {
            loadProductsFromApi { productesResponse ->
                productesResponse?.let {
                    products.value = it.productes
                    println(products.value)
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

        if(index != -1 && cartProducts[index].quantity > 1){
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
        Log.d("count cart products", "${cartProducts.size}.toString()")

    }

    fun isInCart(product: Product): Boolean {
        return cartProducts.any { it.nomProducte == product.nomProducte }
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

    fun loadComandes() {
        viewModelScope.launch {
            loadComandesFromApi(currentUser.value?.idUser.toString()) { comandesResponse ->
                comandesResponse?.let {
                    comandes.value = it.Comandes
                }
            }
        }
    }

    fun loginViewModel(email: String, password: String) {
        viewModelScope.launch {
            val loginRequest = LoginRequest(email, password)
            loginError.value = null

            login(loginRequest) { loginResponse, throwable ->
                Log.d("login", "$loginResponse")
                if(throwable != null) {
                    Log.e("login", "Error de red: ${throwable.message}")
                } else if (loginResponse != null && loginResponse.Confirmacio) {
                    // Login successful
                    Log.d("login", "Credenciales correctas")
                    val user = Usuari(
                        loginResponse.idUser,
                        loginResponse.Nom,
                        loginResponse.Correu,
                        loginResponse.Contrasenya
                    )
                    currentUser.value = user
                    loginError.value = null
                } else {
                    Log.d("login", "Credenciales incorrectas")
                    loginError.value = "Correu o contrasenya incorrectes"
                }
            }
        }
    }
}