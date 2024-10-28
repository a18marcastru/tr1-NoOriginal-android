package com.example.tr1.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tr1.data.loadProductsFromApi
import com.example.tr1.data.loadUsuarisFromJson
import com.example.tr1.model.Product
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

    lateinit var mSocket: Socket

    // Creació de socket
    init {
        viewModelScope.launch {
            try {
                mSocket = IO.socket("http://10.0.2.2:3000")
                mSocket.connect()

                mSocket.on(Socket.EVENT_CONNECT, onConnect)
                mSocket.on("new-product", onNewProduct)
                mSocket.on("delete-product", onDeleteProduct)
                mSocket.on("updated-product", onUpdatedProduct)
                mSocket.on(Socket.EVENT_DISCONNECT) {
                    Log.d("SocketIO", "Disconnected from socket")
                }

                Log.d("SocketIO", "Connected to socket: ${mSocket.id()}")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("SocketIO", "Failed to connect to socket", e)
            }
        }
    }

    // Events de socket

    // Conecta a socket
    private val onConnect = Emitter.Listener { args ->
        Log.d("SocketIO", "Connected to socket")
    }

    // En crear un producte
    private val onNewProduct = Emitter.Listener { args ->
        val productJson = args[0] as String
        val newProduct = Gson().fromJson(productJson, Product::class.java)
        products.value = products.value?.toMutableList()?.apply {
            add(newProduct)
        } ?: listOf(newProduct)
        Log.d("SocketIO", "New product: $newProduct")
    }

    // En esborrar un producte
    private val onDeleteProduct = Emitter.Listener { args ->
        val productId = args[0].toString()
        val deletedProduct = products.value?.find { it.idProducte == productId }
        products.value = products.value?.filterNot { it.idProducte == productId }
        Log.d("SocketIO", "Deleted product: $deletedProduct")
    }

    // En actualitzar un producte
    private val onUpdatedProduct = Emitter.Listener { args ->
        val productJson = args[0] as String
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