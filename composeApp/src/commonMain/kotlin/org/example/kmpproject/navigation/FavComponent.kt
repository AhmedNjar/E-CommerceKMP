package org.example.kmpproject.navigation

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.kmpproject.data.model.CartItemData

class FavComponent(
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit
) : ComponentContext by componentContext, IFavComponent {

    // TODO: اربط بـ favRepo لما تعمله — نفس الـ pattern بتاع cartRepo
    private val _favItems = MutableStateFlow<List<CartItemData>>(emptyList())
    override val favItems: StateFlow<List<CartItemData>> = _favItems.asStateFlow()

    override fun removeFromFav(itemId: String) {
        _favItems.update { items -> items.filter { it.id != itemId } }
    }

    override fun addToCart(itemId: String) {
        val item = _favItems.value.find { it.id == itemId } ?: return
        cartRepo.addToCart(item)
    }

    override fun goBack() = onGoBack()
}