package org.example.kmpproject.ui.main_screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import org.example.kmpproject.data.model.CartItemData
import org.example.kmpproject.navigation.IFavComponent
import org.jetbrains.compose.ui.tooling.preview.Preview

// ── FavPage ───────────────────────────────────────────────────────────────────
@Composable
fun FavPage(component: IFavComponent) {

    val favItems by component.favItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 39.dp)
    ) {

        // ── Top bar ───────────────────────────────────────────────────────
        FavTopBar(
            itemCount = favItems.size,
            onBack    = { component.goBack() }
        )

        Spacer(Modifier.height(24.dp))

        // ── Content ───────────────────────────────────────────────────────
        if (favItems.isEmpty()) {
            FavEmptyState(modifier = Modifier.weight(1f))
        } else {
            LazyColumn(
                modifier              = Modifier.weight(1f),
                verticalArrangement   = Arrangement.spacedBy(14.dp),
                contentPadding        = PaddingValues(bottom = 24.dp)
            ) {
                itemsIndexed(
                    items = favItems,
                    key   = { _, item -> item.id }
                ) { index, item ->
                    AnimatedVisibility(
                        visible = true,
                        enter   = fadeIn(tween(300 + index * 60)) +
                                slideInHorizontally(tween(300 + index * 60)) { -it / 3 }
                    ) {
                        FavItemCard(
                            item          = item,
                            onAddToCart   = { component.addToCart(item.id) },
                            onRemove      = { component.removeFromFav(item.id) }
                        )
                    }
                }
            }
        }
    }
}

// ── Top bar ───────────────────────────────────────────────────────────────────
@Composable
private fun FavTopBar(itemCount: Int, onBack: () -> Unit) {
    Row(
        modifier              = Modifier.fillMaxWidth().height(42.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color(241, 245, 249))
                .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier           = Modifier.size(22.dp)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text       = "Wishlist",
                fontWeight = FontWeight.Bold,
                fontSize   = 18.sp,
                fontFamily = FontFamily.SansSerif
            )
            AnimatedContent(
                targetState  = itemCount,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label        = "count"
            ) { count ->
                Text(
                    text     = if (count == 0) "Empty" else "$count items",
                    fontSize = 12.sp,
                    color    = Color.Gray
                )
            }
        }

        // Heart icon decoration
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color(255, 235, 235)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Filled.Favorite,
                contentDescription = null,
                tint               = Color(255, 80, 80),
                modifier           = Modifier.size(20.dp)
            )
        }
    }
}

// ── Item card ─────────────────────────────────────────────────────────────────
@Composable
private fun FavItemCard(
    item        : CartItemData,
    onAddToCart : () -> Unit,
    onRemove    : () -> Unit
) {
    var addedToCart  by remember { mutableStateOf(false) }
    var removing     by remember { mutableStateOf(false) }

    val cardScale by animateFloatAsState(
        targetValue   = if (removing) 0.92f else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label         = "cardScale"
    )
    val cartBg by animateColorAsState(
        targetValue   = if (addedToCart) Color(76, 175, 80) else Color(77, 160, 255),
        animationSpec = tween(300), label = "cartBg"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // ── Product image ─────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(191, 221, 255))
            ) {
                AsyncImage(
                    model              = item.image,
                    contentDescription = item.title,
                    contentScale       = ContentScale.Crop,
                    modifier           = Modifier.fillMaxSize()
                )
                // gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.15f))
                            )
                        )
                )
            }

            // ── Info ──────────────────────────────────────────────────────
            Column(
                modifier            = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text       = item.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize   = 15.sp,
                    maxLines   = 2,
                    overflow   = TextOverflow.Ellipsis
                )
                Text(
                    text       = "$${item.price}",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize   = 18.sp,
                    color      = Color(77, 160, 255)
                )
                // Add to cart button
                Button(
                    onClick = {
                        addedToCart = true
                        onAddToCart()
                    },
                    modifier  = Modifier.fillMaxWidth().height(36.dp),
                    shape     = RoundedCornerShape(10.dp),
                    colors    = ButtonDefaults.buttonColors(
                        backgroundColor = cartBg,
                        contentColor    = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    AnimatedContent(
                        targetState  = addedToCart,
                        transitionSpec = {
                            (slideInVertically { -it } + fadeIn()) togetherWith
                                    (slideOutVertically { it } + fadeOut())
                        },
                        label = "cartLabel"
                    ) { added ->
                        Row(
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Outlined.ShoppingCart, null, Modifier.size(14.dp))
                            Text(
                                text       = if (added) "Added ✓" else "Add to Cart",
                                fontSize   = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // ── Remove button ─────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(255, 235, 235))
                    .clickable {
                        removing = true
                        onRemove()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Filled.Delete,
                    contentDescription = "Remove",
                    tint               = Color(220, 50, 50),
                    modifier           = Modifier.size(18.dp)
                )
            }
        }
    }
}

// ── Empty state ───────────────────────────────────────────────────────────────
@Composable
private fun FavEmptyState(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val heartScale by infiniteTransition.animateFloat(
        initialValue  = 1f,
        targetValue   = 1.12f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label         = "heartPulse"
    )

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(255, 235, 235))
                    .scale(heartScale),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint               = Color(255, 80, 80),
                    modifier           = Modifier.size(48.dp)
                )
            }
            Text("Your wishlist is empty", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                text     = "Save items you love and find them here",
                fontSize = 14.sp,
                color    = Color.Gray
            )
        }
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────

private val sampleFavItems = listOf(
    CartItemData("1", "Nike Air Max 270",   120, "https://picsum.photos/seed/shoe1/200"),
    CartItemData("2", "Adidas Ultra Boost",  95, "https://picsum.photos/seed/shoe2/200"),
    CartItemData("3", "Puma RS-X 3.0",       75, "https://picsum.photos/seed/shoe3/200"),
)

private fun fakeFavComponent(items: List<CartItemData> = sampleFavItems): IFavComponent =
    object : IFavComponent {
        override val favItems = MutableStateFlow(items)
        override fun removeFromFav(itemId: String) = Unit
        override fun addToCart(itemId: String)     = Unit
        override fun goBack()                      = Unit
    }

@Preview @Composable
private fun FavPagePreview() {
    Surface(color = Color(245, 247, 250)) {
        FavPage(component = fakeFavComponent())
    }
}

@Preview @Composable
private fun FavPageEmptyPreview() {
    Surface(color = Color(245, 247, 250)) {
        FavPage(component = fakeFavComponent(emptyList()))
    }
}