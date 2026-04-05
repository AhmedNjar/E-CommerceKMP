package org.example.kmpproject.navigation

import kotlinx.coroutines.flow.StateFlow

interface IProductComponent {

    // ── State ─────────────────────────────────────────────────────────────
    val productId   : String
    val product     : String
    val price       : String
    val image       : String
    val selectedSize: StateFlow<String>
    val isFavorite  : StateFlow<Boolean>
    val addedToCart : StateFlow<Boolean>   // feedback بعد الـ Add to Cart

    // ── Actions ───────────────────────────────────────────────────────────
    fun selectSize(size: String)
    fun toggleFavorite()
    fun addToCart()
    fun buyNow()
    fun goBack()
}