package org.example.kmpproject.ui.home.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import e_commercekmp.composeapp.generated.resources.Res
import e_commercekmp.composeapp.generated.resources.pfp
import org.example.kmpproject.data.dto.Cloth
import org.example.kmpproject.navigation.HomeEvent
import org.example.kmpproject.navigation.IHomeComponent
import org.jetbrains.compose.resources.painterResource

@Composable
fun RecentViewedBox(
    component: IHomeComponent,
    cloth    : Cloth
) {
    var isFav    by remember { mutableStateOf(false) }
    var pressed  by remember { mutableStateOf(false) }

    // ── Tap scale animation ───────────────────────────────────────────────
    val cardScale by animateFloatAsState(
        targetValue   = if (pressed) 0.96f else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label         = "cardScale"
    )

    // ── Favorite heart animation ──────────────────────────────────────────
    val heartScale by animateFloatAsState(
        targetValue   = if (isFav) 1.3f else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label         = "heartScale"
    )

    Box(
        modifier = Modifier
            .size(145.dp, 190.dp)
            .scale(cardScale)
            .clip(RoundedCornerShape(17.dp))
            .background(Color(77, 160, 255))
            .clickable {
                pressed = true
                component.onEvent(
                    HomeEvent.ClickProduct(
                        productId = cloth.id,
                        product   = cloth.title,
                        price     = cloth.price.toString(),
                        image     = cloth.image
                    )
                )
            }
    ) {
        // ── Product image ─────────────────────────────────────────────────
        AsyncImage(
            model              = cloth.image,
            contentDescription = cloth.title,
            contentScale       = ContentScale.Crop,
            modifier           = Modifier.fillMaxSize(),
            error              = painterResource(Res.drawable.pfp)
        )

        // ── Gradient overlay (bottom) for text readability ────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.65f))
                    )
                )
        )

        // ── Title + price ─────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            Text(
                text     = cloth.title,
                fontSize = 12.sp,
                color    = Color.White,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text     = "$${cloth.price}",
                fontSize = 13.sp,
                color    = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        // ── Favorite button (top-end) ─────────────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(7.dp)
                .size(28.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.85f))
                .clickable { isFav = !isFav },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite",
                tint               = if (isFav) Color(255, 80, 80) else Color.Gray,
                modifier           = Modifier.size(15.dp).scale(heartScale)
            )
        }
    }

    // reset press state
    LaunchedEffect(pressed) {
        if (pressed) { kotlinx.coroutines.delay(150); pressed = false }
    }
}