package com.example.tr1.ui

import androidx.compose.runtime.mutableStateListOf
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tr1.data.loadComandesFromApi
import com.example.tr1.data.loadProductsFromApi
import com.example.tr1.data.login
import com.example.tr1.data.newComanda
import com.example.tr1.data.register
import com.example.tr1.data.updateUser
import com.example.tr1.model.CanviEstat
import com.example.tr1.model.CanviStock
import com.example.tr1.model.Comanda
import com.example.tr1.model.EstatComanda
import com.example.tr1.model.LoginRequest
import com.example.tr1.model.Product
import com.example.tr1.model.ProductePerComanda
import com.example.tr1.model.RegisterRequest
import com.example.tr1.model.UpdateUserRequest
import com.example.tr1.model.Usuari
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.launch
import java.security.MessageDigest

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

    fun getIdUsuari(): Int? {
        return currentUser.value?.idUser
    }

    lateinit var mSocket: Socket

    //Creació de socket
    init {
        viewModelScope.launch {
            try {
                mSocket = IO.socket("http://10.0.2.2:3010")
                //mSocket = IO.socket("http://juicengo.dam.inspedralbes.cat:20871")
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
                mSocket.on("update-stock", onUpdateStock)
                mSocket.on("update-estat", onUpdateState)
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

    private val onUpdateStock = Emitter.Listener { args ->
        val stockJson = args[0] as String
        Log.d("SocketIO", "Canvi a l'stock: $stockJson")
        val updatedStockList = Gson().fromJson(stockJson, Array<CanviStock>::class.java).toList()

        products.value = products.value?.map { existingProduct ->
            val stockChange = updatedStockList.find { it.idProducte == existingProduct.idProducte }
            (if (stockChange != null) {
                existingProduct.copy(Stock = existingProduct.Stock - stockChange.Stock)
            } else {
                existingProduct
            })
        }
        Log.d("SocketIO", "Updated products: ${products.value}")
    }

    private val onUpdateState = Emitter.Listener { args ->
        val stateJson = args[0] as String
        Log.d("SocketIO", "Canvi d'estat: $stateJson")
        val updatedState = Gson().fromJson(stateJson, CanviEstat::class.java)

        val estat = EstatComanda.valueOf(updatedState.Estat)
        Log.d("SocketIO", "Estat nou: $estat")

        val updatedComanda = comandes.value?.find { it.idComanda == updatedState.idComanda }
        Log.d("SocketIO", "Updated comanda: $updatedComanda")
        if (updatedComanda != null) {
            comandes.value = comandes.value?.map {
                if (it.idComanda == updatedState.idComanda) {
                    it.copy(Estat = estat)
                } else {
                    it
                }
            }
        } else {
            Log.e("SocketIO", "Comanda no trobada: ${updatedState.idComanda}")
        }
        Log.d("SocketIO", "Updated comandes: ${comandes.value}")
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

        if (index != -1) {
            cartProducts[index] =
                cartProducts[index].copy(quantity = cartProducts[index].quantity + 1)
        }
    }

    fun decrementProductQuantity(product: Product) {
        val index = cartProducts.indexOf(product)

        if (index != -1 && cartProducts[index].quantity > 1) {
            cartProducts[index] =
                cartProducts[index].copy(quantity = cartProducts[index].quantity - 1)
        }
    }

    fun addToCart(product: Product) {
        val existingProduct = cartProducts.find { it.nomProducte == product.nomProducte }
        if (existingProduct != null) {
            incrementProductQuantity(existingProduct)
        } else {
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

    fun getTotalPrice(): Double {
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

    fun transformProductsToOrderProducts(products: List<Product>): List<ProductePerComanda> {
        return products.map { product ->
            ProductePerComanda(
                idProducte = product.idProducte,
                nomProducte = product.nomProducte,
                quantitat = product.quantity,
                preuTotalProducte = product.Preu * product.quantity
            )
        }
    }

    fun novaComanda(comanda: Comanda) {
        viewModelScope.launch {
            Log.d("TakeAwayApp", "Nova comanda: $comanda")
            newComanda(comanda)
        }
    }

    fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun loginViewModel(email: String, password: String) {
        viewModelScope.launch {
            val hashedPassword = hashPassword(password)
            val loginRequest = LoginRequest(email, hashedPassword)

            loginError.value = null

            login(loginRequest) { loginResponse, throwable ->
                Log.d("login", "$loginResponse")
                if (throwable != null) {
                    Log.e("login", "Error de xarxa: ${throwable.message}")
                } else if (loginResponse != null && loginResponse.Confirmacio) {
                    // Login successful
                    Log.d("login", "Credencials correctes")
                    val user = Usuari(
                        loginResponse.idUser,
                        loginResponse.Nom,
                        loginResponse.Correu,
                        loginResponse.Contrasenya
                    )
                    currentUser.value = user
                    loginError.value = null
                } else {
                    Log.d("login", "Credencials incorrectes")
                    loginError.value = "Correu o contrasenya incorrectes"
                }
            }
        }
    }

    fun registerViewModel(name: String, email: String, password: String) {
        viewModelScope.launch {
            val hashedPassword = hashPassword(password)
            val registerRequest = RegisterRequest(name, email, hashedPassword)
            Log.d("register", "Request: $registerRequest")
            loginError.value = null

            register(registerRequest) { RegisterResponse, throwable ->
                Log.d("register", "$RegisterResponse")
                if (throwable != null) {
                    Log.e("register", "Error de red: ${throwable.message}")
                } else if (RegisterResponse != null && RegisterResponse.Confirmacio) {
                    // Register successful
                    Log.d("register", "Credenciales correctas")
                    val user = Usuari(
                        RegisterResponse.idUser,
                        RegisterResponse.Nom,
                        RegisterResponse.Correu,
                        RegisterResponse.Contrasenya
                    )
                    currentUser.value = user
                    loginError.value = null
                } else {
                    Log.d("register", "Credenciales incorrectas")
                    loginError.value = "Correu o contrasenya incorrectes"
                }
            }
        }
    }

    fun updateUserViewModel(data: UpdateUserRequest) {
        viewModelScope.launch {
            //updateUser(id, data)
            Log.d("updateUser", "Actualitzant usuari: $data")
            updateUser(currentUser.value?.idUser.toString(), data)
        }


    }
    fun logout() {
        // Limpiar el usuario actual
        currentUser.value = null
        // Limpiar otros datos de sesión si es necesario
        cartProducts.clear()  // Si quieres limpiar el carrito también
        // Otras posibles acciones de limpieza de sesión
    }


}