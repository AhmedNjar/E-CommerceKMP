package org.example.kmpproject.ui.cart

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import org.example.kmpproject.data.model.CartItemData
import org.example.kmpproject.navigation.ICartComponent
import org.example.kmpproject.ui.cart.components.CartItem
import org.example.kmpproject.ui.cart.components.CartTopBar
import org.example.kmpproject.ui.cart.components.CheckOut
import org.jetbrains.compose.ui.tooling.preview.Preview


// ── CartPage ──────────────────────────────────────────────────────────────────
@Composable
fun CartPage(component: ICartComponent) {

    // collect state from component (ViewModel)
    val cartItems    by component.cartItems.collectAsState()
    val promoCode    by component.promoCode.collectAsState()
    val promoApplied by component.promoApplied.collectAsState()
    val orderAmount  by component.orderAmount.collectAsState()
    val discount     by component.discount.collectAsState()
    val total        by component.total.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 39.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        CartTopBar(pageName = "Cart", component = component)

        Spacer(Modifier.height(20.dp))

        // ── Item list or empty state ──────────────────────────────────────
        if (cartItems.isEmpty()) {
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🛒", fontSize = 56.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Your cart is empty",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                    Text(
                        text = "Add items to get started",
                        fontSize = 14.sp,
                        color = Color(180, 180, 180)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                itemsIndexed(cartItems, key = { _, item -> item.id }) { _, item ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(tween(300)) + slideInHorizontally(
                            initialOffsetX = { it / 2 }, animationSpec = tween(300)
                        ),
                        exit = fadeOut(tween(200)) + slideOutHorizontally(
                            targetOffsetX = { it }, animationSpec = tween(200)
                        )
                    ) {
                        CartItem(
                            title              = item.title,
                            price              = item.price,
                            image              = item.image,
                            quantity           = item.quantity,
                            onQuantityIncrease = { component.increaseQuantity(item.id) },
                            onQuantityDecrease = { component.decreaseQuantity(item.id) },
                            onRemove           = { component.removeItem(item.id) }
                        )
                    }
                }
            }
        }

        // ── Checkout panel ────────────────────────────────────────────────
        CheckOut(
            promoCode         = promoCode,
            promoApplied      = promoApplied,
            orderAmount       = "$$orderAmount",
            discount          = "$$discount",
            total             = "$$total",
            onPromoCodeChange = { component.onPromoCodeChange(it) },
            onApplyClick      = { component.applyPromo() },
            onCheckoutClick   = { component.checkout() }
        )
    }
}

// ── Preview helpers ───────────────────────────────────────────────────────────

private val sampleItems = listOf(
    CartItemData("1", "Nike Air Max 270",  120, "https://picsum.photos/seed/shoe1/200"),
    CartItemData("2", "Adidas Ultra Boost", 95, "https://picsum.photos/seed/shoe2/200"),
    CartItemData("3", "Puma RS-X 3.0",      75, "https://picsum.photos/seed/shoe3/200"),
)

/** Fake component — no Decompose / no navigation needed */
private fun fakeComponent(items: List<CartItemData> = sampleItems): ICartComponent =
    object : ICartComponent {
        override val cartItems    = MutableStateFlow(items)
        override val promoCode    = MutableStateFlow("")
        override val promoApplied = MutableStateFlow(false)
        override val orderAmount  = MutableStateFlow(items.sumOf { it.price * it.quantity })
        override val discount     = MutableStateFlow(0)
        override val total        = MutableStateFlow(items.sumOf { it.price * it.quantity })

        override fun increaseQuantity(itemId: String)   = Unit
        override fun decreaseQuantity(itemId: String)   = Unit
        override fun removeItem(itemId: String)         = Unit
        override fun onPromoCodeChange(code: String) = Unit
        override fun applyPromo()                    = Unit
        override fun checkout()                      = Unit
        override fun goBack()                        = Unit
    }

@Preview @Composable
private fun CartPagePreview() {
    Surface { CartPage(component = fakeComponent()) }
}

@Preview @Composable
private fun CartPageEmptyPreview() {
    Surface { CartPage(component = fakeComponent(emptyList())) }
}

@Preview @Composable
private fun CartPageSinglePreview() {
    Surface { CartPage(component = fakeComponent(sampleItems.take(1))) }
}