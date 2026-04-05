package org.example.kmpproject.navigation

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.kmpproject.data.model.CartItemData

class ProductComponent(
    override val productId   : String,
    override val product     : String,
    override val price       : String,
    override val image       : String,
    private val onGoBack     : () -> Unit,
    private val onBuyNow     : () -> Unit = {},
    componentContext         : ComponentContext
) : ComponentContext by componentContext, IProductComponent {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _selectedSize = MutableStateFlow("M")
    private val _isFavorite   = MutableStateFlow(false)
    private val _addedToCart  = MutableStateFlow(false)

    override val selectedSize: StateFlow<String>  = _selectedSize.asStateFlow()
    override val isFavorite  : StateFlow<Boolean> = _isFavorite.asStateFlow()
    override val addedToCart : StateFlow<Boolean> = _addedToCart.asStateFlow()

    override fun selectSize(size: String) {
        _selectedSize.value = size
    }

    override fun toggleFavorite() {
        _isFavorite.value = !_isFavorite.value
    }

    override fun addToCart() {
        cartRepo.addToCart(
            CartItemData(
                id    = productId,
                title = product,
                price = price.toIntOrNull() ?: 0,
                image = image
            )
        )
        // ── بيظهر feedback "Added!" لمدة ثانيتين ────────────────────────
        scope.launch {
            _addedToCart.value = true
            delay(2000)
            _addedToCart.value = false
        }
    }

    override fun buyNow() {
        addToCart()
        onBuyNow()
    }

    override fun goBack() = onGoBack()
}