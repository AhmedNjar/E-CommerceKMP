package org.example.kmpproject.navigation

import kotlinx.coroutines.flow.StateFlow
import org.example.kmpproject.data.model.CartItemData

interface ICartComponent {
    val cartItems   : StateFlow<List<CartItemData>>
    val promoCode   : StateFlow<String>
    val promoApplied: StateFlow<Boolean>
    val orderAmount : StateFlow<Int>
    val discount    : StateFlow<Int>
    val total       : StateFlow<Int>

    fun increaseQuantity(itemId: String)
    fun decreaseQuantity(itemId: String)
    fun removeItem(itemId: String)
    fun onPromoCodeChange(code: String)
    fun applyPromo()
    fun checkout()
    fun goBack()
}