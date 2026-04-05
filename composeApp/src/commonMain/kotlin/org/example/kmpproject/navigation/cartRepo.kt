package org.example.kmpproject.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.kmpproject.data.model.CartItemData

object cartRepo {

    private val _cartItems = MutableStateFlow<List<CartItemData>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    // ── Add (لو الـ item موجود بالفعل يزود الـ quantity) ─────────────────
    fun addToCart(item: CartItemData) {
        _cartItems.update { current ->
            val existing = current.find { it.id == item.id }
            if (existing != null) {
                current.map {
                    if (it.id == item.id) it.copy(quantity = it.quantity + 1) else it
                }
            } else {
                current + item
            }
        }
    }

    fun increaseQuantity(itemId: String) {
        _cartItems.update { items ->
            items.map { if (it.id == itemId) it.copy(quantity = it.quantity + 1) else it }
        }
    }

    fun decreaseQuantity(itemId: String) {
        _cartItems.update { items ->
            items.map {
                if (it.id == itemId && it.quantity > 1) it.copy(quantity = it.quantity - 1) else it
            }
        }
    }

    fun removeItem(itemId: String) {
        _cartItems.update { items -> items.filter { it.id != itemId } }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
}