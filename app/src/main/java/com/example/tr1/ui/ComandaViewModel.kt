package com.example.tr1.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.tr1.model.Product

class ComandaViewModel : ViewModel() {

    private val _cistella = mutableStateListOf<Product>()
    var cistella: List<Product> = _cistella

    fun addToCart(product: Product) {
        _cistella.add(product)
    }

    fun removeFromCart(product: Product) {
        _cistella.remove(product)
    }

    fun clearCart() {
        _cistella.clear()
    }
}