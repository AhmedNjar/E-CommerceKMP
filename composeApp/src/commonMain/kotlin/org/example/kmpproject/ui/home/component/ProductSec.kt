package org.example.kmpproject.ui.home.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProductSec(onShopNow: () -> Unit = {}) {

    // ── Subtle shimmer animation on the banner ────────────────────────────
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue   = 0f,
        targetValue    = 1f,
        animationSpec  = infiniteRepeatable(tween(3000), RepeatMode.Reverse),
        label          = "shimmerOffset"
    )

    val bannerColor = Color(77, 160, 255)
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            bannerColor,
            Color(shimmerOffset * 30 + 100, shimmerOffset * 60 + 180, 255f),
            bannerColor
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(gradientBrush)
            .clickable { onShopNow() }
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text       = "Summer Sale 🔥",
                fontSize   = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color      = Color.White.copy(alpha = 0.85f)
            )
            Text(
                text       = "Up to\n50% Off",
                fontSize   = 32.sp,
                fontWeight = FontWeight.Bold,
                color      = Color.White,
                lineHeight = 36.sp
            )
            Spacer(Modifier.height(4.dp))
            Button(
                onClick = onShopNow,
                shape   = RoundedCornerShape(20.dp),
                colors  = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text       = "Shop Now",
                    color      = bannerColor,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 13.sp
                )
            }
        }

        // Decorative circles
        Box(
            modifier = Modifier
                .size(130.dp)
                .align(Alignment.CenterEnd)
                .offset(x = 20.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White.copy(alpha = 0.12f))
        )
        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-10).dp, y = 20.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White.copy(alpha = 0.08f))
        )
    }
}