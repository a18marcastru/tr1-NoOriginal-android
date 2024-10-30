package com.example.tr1.ui

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

    var comandes = mutableStateOf<List<Comanda>?>(null)
        private set

    var currentUser = mutableStateOf<Usuari?>(null)
        private set

    lateinit var mSocket: Socket

     //Creació de socket
    init {
        viewModelScope.launch {
            try {
                mSocket = IO.socket("http://10.0.2.2:3010")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("SocketIO", "Failed to connect to socket", e)
            }
            mSocket.connect()
            mSocket.on(Socket.EVENT_CONNECT) {
                Log.d("SocketIO", "Connected to socket")
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
        Log.d("SocketIO", "Producte a actualitzar: $productJson")
        val newProduct = Gson().fromJson(productJson, Product::class.java)
        products.value = products.value?.toMutableList()?.apply {
            add(newProduct)
        } ?: listOf(newProduct)
        Log.d("SocketIO", "New product: $newProduct")
    }

    // En esborrar un producte
    private val onDeleteProduct = Emitter.Listener { args ->
        val productId = args[0]
        val deletedProduct = products.value?.find { it.idProducte == productId }
        products.value = products.value?.filterNot { it.idProducte == productId }
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
                }
            }
        }
    }

    fun loadComandes() {
        viewModelScope.launch {
            loadComandesFromApi { comandesResponse ->
                comandesResponse?.let {
                    comandes.value = it.comandes
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
                        loginResponse.idUser.toString(),
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