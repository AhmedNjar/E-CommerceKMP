package org.example.kmpproject.ui.product

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.kmpproject.navigation.IProductComponent
import org.example.kmpproject.ui.product.components.ProdDetails
import org.example.kmpproject.ui.product.components.ProductPics
import org.example.kmpproject.ui.product.components.TopBar
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProductPage(component: IProductComponent) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 39.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopBar(pageName  = component.product, component = component)
        ProductPics(component = component)
        ProdDetails(component = component)
    }
}

// ── Preview helpers ───────────────────────────────────────────────────────────

private fun fakeProductComponent(
    productId : String  = "p1",
    product   : String  = "Nike Air Max 270",
    price     : String  = "120",
    image     : String  = "https://picsum.photos/seed/shoe1/400/400",
    isFav     : Boolean = false,
    addedToCart: Boolean = false,
    selectedSize: String = "M"
): IProductComponent = object : IProductComponent {
    override val productId    = productId
    override val product      = product
    override val price        = price
    override val image        = image
    override val selectedSize = MutableStateFlow(selectedSize)
    override val isFavorite   = MutableStateFlow(isFav)
    override val addedToCart  = MutableStateFlow(addedToCart)

    override fun selectSize(size: String) = Unit
    override fun toggleFavorite()         = Unit
    override fun addToCart()              = Unit
    override fun buyNow()                 = Unit
    override fun goBack()                 = Unit
}

// Preview 1 — Normal product
@Preview
@Composable
private fun ProductPagePreview() {
    Surface {
        ProductPage(component = fakeProductComponent())
    }
}

// Preview 2 — Favorited + Added to cart state
@Preview
@Composable
private fun ProductPageFavAddedPreview() {
    Surface {
        ProductPage(
            component = fakeProductComponent(
                isFav       = true,
                addedToCart = true,
                selectedSize = "L"
            )
        )
    }
}

// Preview 3 — Long product name
@Preview
@Composable
private fun ProductPageLongNamePreview() {
    Surface {
        ProductPage(
            component = fakeProductComponent(
                product = "Adidas Ultra Boost 22 Running Shoes",
                price   = "195",
                image   = "https://picsum.photos/seed/shoe2/400/400"
            )
        )
    }
}