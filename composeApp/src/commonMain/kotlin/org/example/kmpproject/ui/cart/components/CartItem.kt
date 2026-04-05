package org.example.kmpproject.ui.cart.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun CartItem(
    title: String,
    price: Int,
    image: String,
    quantity: Int = 1,
    onQuantityIncrease: () -> Unit = {},
    onQuantityDecrease: () -> Unit = {},
    onRemove: () -> Unit = {},
    onFavoriteToggle: () -> Unit = {}
) {
    var isFavorite by remember { mutableStateOf(false) }

    // ── Favorite heart animation ──────────────────────────────────────────
    val heartScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.25f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "heartScale"
    )
    val heartColor by animateColorAsState(
        targetValue = if (isFavorite) Color(255, 119, 76) else Color(180, 180, 180),
        label = "heartColor"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(17.dp))
            .background(Color(191, 221, 255))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ── Product Image ─────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White)
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )

                // Favorite button (top-end of image)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(5.dp)
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(Color(191, 221, 255))
                        .clickable {
                            isFavorite = !isFavorite
                            onFavoriteToggle()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorite",
                        tint = heartColor,
                        modifier = Modifier.scale(heartScale * 0.7f)
                    )
                }
            }

            // ── Title + Price + Quantity ───────────────────────────────────
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "$$price",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1A73E8)
                )

                // Quantity +/- controls
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuantityButton(
                        label = "−",
                        enabled = quantity > 1,
                        onClick = onQuantityDecrease
                    )
                    Text(
                        text = "$quantity",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier.widthIn(min = 18.dp),
                    )
                    QuantityButton(
                        label = "+",
                        onClick = onQuantityIncrease
                    )
                }
            }

            // ── Remove (X) button ─────────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.55f))
                    .clickable { onRemove() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Remove item",
                    tint = Color(100, 100, 100),
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

// ── Reusable +/- button ───────────────────────────────────────────────────────
@Composable
private fun QuantityButton(
    label: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (enabled) Color.White else Color(220, 220, 220),
        label = "qtyBg"
    )
    Box(
        modifier = Modifier
            .size(26.dp)
            .clip(CircleShape)
            .background(bgColor)
            .border(1.dp, Color(200, 200, 200), CircleShape)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = if (enabled) Color.Black else Color.Gray
        )
    }
}