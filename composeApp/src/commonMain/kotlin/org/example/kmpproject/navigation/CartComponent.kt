package org.example.kmpproject.navigation

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.kmpproject.data.model.CartItemData

private val PROMO_CODES = mapOf(
    "SAVE10"  to 10,
    "SAVE20"  to 20,
    "WELCOME" to 15
)

class CartComponent(
    componentContext: ComponentContext,
    private val onGoBack   : () -> Unit,
    private val onCheckout : () -> Unit = {}
) : ComponentContext by componentContext, ICartComponent {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // ── cartRepo هو الـ single source of truth للـ cart data ─────────────
    override val cartItems: StateFlow<List<CartItemData>> = cartRepo.cartItems

    // ── Promo ─────────────────────────────────────────────────────────────
    private val _promoCode    = MutableStateFlow("")
    private val _promoApplied = MutableStateFlow(false)
    override val promoCode    : StateFlow<String>  = _promoCode.asStateFlow()
    override val promoApplied : StateFlow<Boolean> = _promoApplied.asStateFlow()

    // ── Totals بيتحسبوا من cartRepo مباشرة ───────────────────────────────
    override val orderAmount: StateFlow<Int> = cartRepo.cartItems
        .map { items -> items.sumOf { it.price * it.quantity } }
        .stateIn(scope, SharingStarted.Eagerly, 0)

    override val discount: StateFlow<Int> =
        combine(_promoCode, _promoApplied) { code, applied ->
            if (applied) PROMO_CODES[code.uppercase()] ?: 0 else 0
        }.stateIn(scope, SharingStarted.Eagerly, 0)

    override val total: StateFlow<Int> =
        combine(orderAmount, discount) { amount, disc ->
            (amount - disc).coerceAtLeast(0)
        }.stateIn(scope, SharingStarted.Eagerly, 0)

    // ── Actions delegate to cartRepo ──────────────────────────────────────
    override fun increaseQuantity(itemId: String)   = cartRepo.increaseQuantity(itemId)
    override fun decreaseQuantity(itemId: String)   = cartRepo.decreaseQuantity(itemId)
    override fun removeItem(itemId: String)         = cartRepo.removeItem(itemId)

    override fun onPromoCodeChange(code: String) {
        _promoCode.value    = code
        _promoApplied.value = false
    }

    override fun applyPromo() {
        _promoApplied.value = _promoCode.value.uppercase() in PROMO_CODES
    }

    override fun checkout() {
        scope.launch { onCheckout() }
    }

    override fun goBack() = onGoBack()
}