package org.example.kmpproject.navigation

import kotlinx.coroutines.flow.StateFlow
import org.example.kmpproject.data.model.CartItemData

interface IFavComponent {
    val favItems: StateFlow<List<CartItemData>>

    fun removeFromFav(itemId: String)
    fun addToCart(itemId: String)
    fun goBack()
}