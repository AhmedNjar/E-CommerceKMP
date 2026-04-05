package org.example.kmpproject.ui.product.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import org.example.kmpproject.navigation.IProductComponent

@Composable
fun ProductPics(component: IProductComponent) {

    val isFavorite    by component.isFavorite.collectAsState()
    var selectedIndex by remember { mutableStateOf(0) }

    // ── Heart bounce ──────────────────────────────────────────────────────
    val heartScale by animateFloatAsState(
        targetValue   = if (isFavorite) 1.35f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness    = Spring.StiffnessMedium
        ),
        label = "heart"
    )

    // ── Main image fade on thumbnail change ───────────────────────────────
    val imageAlpha by animateFloatAsState(
        targetValue   = 1f,
        animationSpec = tween(300),
        label         = "imgAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
    ) {

        // ── Main image ────────────────────────────────────────────────────
        SubcomposeAsyncImage(
            model              = component.image,
            contentDescription = component.product,
            contentScale       = ContentScale.Crop,
            modifier           = Modifier
                .fillMaxWidth()
                .height(295.dp)
                .clip(RoundedCornerShape(24.dp))
                .graphicsLayer { alpha = imageAlpha }
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading) {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(295.dp)
                        .clip(RoundedCornerShape(24.dp))
                )
            } else {
                SubcomposeAsyncImageContent()
            }
        }

        // ── Thumbnail strip ───────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment     = Alignment.CenterVertically
        ) {
            repeat(3) { index ->
                val isSelected = selectedIndex == index

                val thumbScale by animateFloatAsState(
                    targetValue   = if (isSelected) 1.05f else 1f,
                    animationSpec = spring(Spring.DampingRatioMediumBouncy),
                    label         = "thumb$index"
                )
                val borderColor by animateColorAsState(
                    targetValue   = if (isSelected) Color.Black else Color.Transparent,
                    animationSpec = tween(200),
                    label         = "thumbBorder$index"
                )

                Box(
                    modifier = Modifier
                        .size(79.dp, 89.dp)
                        .scale(thumbScale)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(191, 221, 255))
                        .border(2.dp, borderColor, RoundedCornerShape(14.dp))
                        .clickable { selectedIndex = index }
                ) {
                    AsyncImage(
                        model              = component.image,
                        contentDescription = null,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // ── Favorite button ───────────────────────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.92f))
                .clickable { component.toggleFavorite() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = if (isFavorite) Icons.Filled.Favorite
                else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite",
                tint               = if (isFavorite) Color(255, 80, 80) else Color(180, 180, 180),
                modifier           = Modifier
                    .size(22.dp)
                    .scale(heartScale)
            )
        }
    }
}

// ── Shimmer loading effect ────────────────────────────────────────────────────
@Composable
fun ShimmerBox(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val shimmerX by transition.animateFloat(
        initialValue  = -300f,
        targetValue   = 1000f,
        animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing)),
        label         = "shimmerX"
    )
    Box(
        modifier = modifier.background(
            Brush.linearGradient(
                colors      = listOf(
                    Color(220, 220, 220),
                    Color(240, 240, 240),
                    Color(220, 220, 220)
                ),
                start = Offset(shimmerX, 0f),
                end   = Offset(shimmerX + 300f, 300f)
            )
        )
    )
}